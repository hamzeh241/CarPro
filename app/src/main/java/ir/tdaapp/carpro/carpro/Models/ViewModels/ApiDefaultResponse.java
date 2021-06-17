package ir.tdaapp.carpro.carpro.Models.ViewModels;

import java.util.ArrayList;

public class ApiDefaultResponse {

  int code;
  boolean result;
  ArrayList<String> messages;
  String titel;
  boolean reDirect;



  public ApiDefaultResponse(int code, boolean result, ArrayList<String> messages, String titel, boolean reDirect) {
    this.code = code;
    this.result = result;
    this.messages = messages;
    this.titel = titel;
    this.reDirect = reDirect;
  }

  public ApiDefaultResponse() {
  }


  public boolean isReDirect() {
    return reDirect;
  }

  public void setReDirect(boolean reDirect) {
    this.reDirect = reDirect;
  }

  public String getTitel() {
    return titel;
  }

  public void setTitel(String titel) {
    this.titel = titel;
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
