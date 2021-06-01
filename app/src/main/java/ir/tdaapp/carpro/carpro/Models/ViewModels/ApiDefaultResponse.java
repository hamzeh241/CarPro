package ir.tdaapp.carpro.carpro.Models.ViewModels;

import java.util.ArrayList;

public class ApiDefaultResponse {

  int code;
  boolean result;
  ArrayList<String> messages;

  public ApiDefaultResponse(int code, boolean result, ArrayList<String> messages) {
    this.code = code;
    this.result = result;
    this.messages = messages;
  }

  public ApiDefaultResponse() {
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public boolean isResult() {
    return result;
  }

  public void setResult(boolean result) {
    this.result = result;
  }

  public ArrayList<String> getMessages() {
    return messages;
  }

  public void setMessages(ArrayList<String> messages) {
    this.messages = messages;
  }
}
