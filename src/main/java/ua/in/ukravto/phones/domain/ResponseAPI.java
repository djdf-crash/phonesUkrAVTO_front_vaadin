package ua.in.ukravto.phones.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseAPI<T> {
    @SerializedName("result")
    @Expose
    private Boolean result;
    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("body")
    @Expose
    private T body;

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public String getError() {
        if (error == null){
            error = "";
        }
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }
}
