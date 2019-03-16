package ua.in.ukravto.phones.view;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinResponse;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinServletResponse;
import retrofit2.Response;
import ua.in.ukravto.phones.domain.Employee;
import ua.in.ukravto.phones.domain.EmployeeOrganization;
import ua.in.ukravto.phones.domain.ResponseListAPI;
import ua.in.ukravto.phones.repo.PhonesAPI;
import ua.in.ukravto.phones.repo.RestClientBuilder;
import ua.in.ukravto.phones.repo.utils.ConstantAPI;
import ua.in.ukravto.phones.repo.utils.CookieManager;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Route("main")
public class MainView extends VerticalLayout {

    private Grid<Employee> mGrid = new Grid<>(Employee.class, false);
    private List<Employee> mListEmp = new ArrayList<>();
    private ListDataProvider<Employee> mDataProviderGrid;
    private List<Employee> mListEmpFiltered = new ArrayList<>();
    private List<EmployeeOrganization> mListOrg = new ArrayList<>();
    private List<EmployeeOrganization> mListOrgFiltered = new ArrayList<>();
    private Select<EmployeeOrganization> mSelectOrg;
    private PhonesAPI mAPI;
    private String token;


    public MainView() {

        CookieManager cm = new CookieManager((HttpServletResponse) VaadinService.getCurrentResponse());
        Cookie c = cm.getCookieByName(ConstantAPI.COOKIE_NAME);
        if (c == null){
            UI.getCurrent().navigate(StartMain.class);
            UI.getCurrent().getPage().reload();
            return;
        }

        this.token = c.getValue();
        if (this.token.isEmpty()){
            UI.getCurrent().navigate(StartMain.class);
            UI.getCurrent().getPage().reload();
            return;
        }
        this.mAPI = RestClientBuilder.getService();

        setupMainUI();

        getDataAPI();

    }

    private void getDataAPI() {
        Response<ResponseListAPI<Employee>> responseEmp;
        Response<ResponseListAPI<EmployeeOrganization>> responseOrg;
        try {
            responseEmp = mAPI.listEmployeePhones(token).execute();
            responseOrg = mAPI.getListOrganizations(token).execute();
        } catch (IOException e) {
            return;
        }

        if (responseOrg.isSuccessful() && responseOrg.body() != null && responseOrg.body().getResult()) {
            mListOrg = responseOrg.body().getBody();
            if (!mListOrg.isEmpty()) {
                mListOrg.stream()
                        .filter(e -> !e.getIsDelete())
                        .forEach(mListOrgFiltered::add);

                createSelector(mListOrgFiltered);
            }
        }

        if (responseEmp.isSuccessful() && responseEmp.body() != null && responseEmp.body().getResult()) {
            mListEmp = responseEmp.body().getBody();
            if (!mListEmp.isEmpty()) {
                mListEmp.stream()
                        .filter(e -> !e.getDelete())
                        .filter(e -> !e.getPhoneMobile().isEmpty())
                        .forEach(mListEmpFiltered::add);

                createColumnGrid(mListEmpFiltered);
            }
        }
    }

    private Boolean caseInsensitiveContains(String where, String what) {
        return where.toLowerCase().contains(what.toLowerCase());
    }

    private void createSelector(List<EmployeeOrganization> mListOrgFiltered) {

        mListOrgFiltered.sort(Comparator.comparing(EmployeeOrganization::getName));

        mSelectOrg.setEmptySelectionCaption("ALL");
        mSelectOrg.setEmptySelectionAllowed(true);
        mSelectOrg.setTextRenderer(o -> o.getName().toUpperCase());
        mSelectOrg.setItems(mListOrgFiltered);
        mSelectOrg.addValueChangeListener((HasValue.ValueChangeListener<AbstractField.ComponentValueChangeEvent<Select<EmployeeOrganization>, EmployeeOrganization>>) selectOrganizationEvent -> {
            setFilterBySelector(selectOrganizationEvent.getValue());
        });
    }

    private void setFilterBySelector(EmployeeOrganization value) {
        if (value != null){
            Integer filter = value.getID();
            mDataProviderGrid.setFilterByValue(Employee::getOrganizationId, filter);
        }else {
            mDataProviderGrid.clearFilters();
        }
        mDataProviderGrid.refreshAll();
    }

    private void setupMainUI() {

        Button logout = new Button("Logout");
        logout.addClickListener((ComponentEventListener<ClickEvent<Button>>) buttonClickEvent -> {
            CookieManager cookieManager = new CookieManager((VaadinServletResponse) VaadinResponse.getCurrent());
            cookieManager.destroyCookieByName(ConstantAPI.COOKIE_NAME);
            UI.getCurrent().getSession().close();
            logout.getUI().ifPresent(ui -> ui.navigate(StartMain.class));
//            UI.getCurrent().navigate("");
//            UI.getCurrent().getPage().reload();
        });
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        setHeightFull();

        mSelectOrg = new Select<>();
        mSelectOrg.setWidthFull();
        mSelectOrg.setPlaceholder("Select you need organization");

        add(logout, mSelectOrg, mGrid);
    }

    private void createColumnGrid(List<Employee> mListFiltered) {

        mDataProviderGrid = DataProvider.ofCollection(mListFiltered);

        mGrid.setDataProvider(mDataProviderGrid);

        mGrid.setColumnReorderingAllowed(true);


        mGrid.addColumn(Employee::getiD)
                .setHeader("ID")
                .setResizable(true)
                .setSortable(true);

        Grid.Column<Employee> colOrg = mGrid.addColumn(Employee::getOrganizationName);
        colOrg.setHeader("Organization")
                .setSortable(true)
                .setResizable(true);

        Grid.Column<Employee> colDep = mGrid.addColumn(Employee::getDepartment);
        colDep.setHeader("Department")
                .setSortable(true)
                .setResizable(true);

        Grid.Column<Employee> colSec = mGrid.addColumn(Employee::getSection);
        colSec.setHeader("Section")
                .setSortable(true)
                .setResizable(true);

        mGrid.addColumn(Employee::getPost)
                .setHeader("Post")
                .setResizable(true)
                .setSortable(true);

        mGrid.addColumn(Employee::getOrganizationId)
                .setHeader("Organization ID")
                .setVisible(false);

        Grid.Column<Employee> colName = mGrid.addColumn(Employee::getFullName)
                .setHeader("Full name")
                .setSortable(true)
                .setResizable(true);

        Grid.Column<Employee> colPhoneMob = mGrid.addColumn(Employee::getPhoneMobile)
                .setHeader("Phone mobile")
                .setSortable(true)
                .setResizable(true);

        mGrid.addColumn(Employee::getPhone)
                .setHeader("Phone")
                .setSortable(true)
                .setResizable(true);

        mGrid.addColumn(Employee::getEmail)
                .setHeader("Email")
                .setSortable(true)
                .setResizable(true);

        TextField searchFieldName = new TextField("Full name", "Search by name");
        searchFieldName.setWidthFull();
        searchFieldName.addValueChangeListener((HasValue.ValueChangeListener<AbstractField.ComponentValueChangeEvent<TextField, String>>) valueChangeEvent -> {
            if (!valueChangeEvent.getValue().trim().equals("")){
                setFilterBySelector(mSelectOrg.getValue());
                mDataProviderGrid.addFilterByValue(Employee::getFullName, valueChangeEvent.getValue());
                mDataProviderGrid.refreshAll();
            }else {
                setFilterBySelector(mSelectOrg.getValue());
            }
        });

        colName.setHeader(searchFieldName);
    }
}
