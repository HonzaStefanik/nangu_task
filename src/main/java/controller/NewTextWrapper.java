package controller;

public class NewTextWrapper {
  private String text;

  public NewTextWrapper(String text) {
    this.text = text;
  }

  public NewTextWrapper() {
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

}
