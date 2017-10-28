package com.objectivasoftware.atl.base.wait;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import java.lang.reflect.Field;

public final class MyElementLocatorFactory implements ElementLocatorFactory {
  private final SearchContext searchContext;

  public MyElementLocatorFactory(SearchContext searchContext) {
    this.searchContext = searchContext;
  }

  public ElementLocator createLocator(Field field) {
    return new MyElementLocator(searchContext, field);
  }
}