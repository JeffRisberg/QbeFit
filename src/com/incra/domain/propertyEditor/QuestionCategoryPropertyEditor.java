package com.incra.domain.propertyEditor;

import java.beans.PropertyEditorSupport;

import com.incra.domain.QuestionCategory;
import com.incra.services.QuestionCategoryService;

public class QuestionCategoryPropertyEditor extends PropertyEditorSupport {
  private final QuestionCategoryService questionCategoryService;

  public QuestionCategoryPropertyEditor(
      QuestionCategoryService questionCategoryService) {
    this.questionCategoryService = questionCategoryService;
  }

  @Override
  public void setAsText(String text) throws IllegalArgumentException {
    setValue(questionCategoryService.findEntityById(Integer.parseInt(text)));
  }

  @Override
  public String getAsText() {
    Object value = getValue();

    if (value instanceof QuestionCategory) {
      return String.valueOf(((QuestionCategory) value).getId());
    } else {
      return super.getAsText();
    }
  }
}
