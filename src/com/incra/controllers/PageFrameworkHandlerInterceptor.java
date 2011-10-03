package com.incra.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.incra.services.PageFrameworkService;

/**
 * The <i>PageFrameworkHandlerInterceptor</i> is wrapped around the handling of
 * HttpRequests, and assures that the PageFrameworkService is notified of
 * requests being started, as well as augmenting the information in the
 * ModuleAndView to include module navigation information from the
 * PageFrameworkService.
 * 
 * @author Jeff Risberg
 * @since 01/18/11
 */
public class PageFrameworkHandlerInterceptor implements HandlerInterceptor {

  @Autowired
  private PageFrameworkService pageFrameworkService;

  @Override
  public boolean preHandle(HttpServletRequest request,
      HttpServletResponse response, Object handler) throws Exception {
    HttpSession session = request.getSession(true);

    pageFrameworkService.setIsRedirect(session, Boolean.FALSE);
    pageFrameworkService.setCurrentMenuItem(request);
    return true;
  }

  @Override
  public void afterCompletion(HttpServletRequest request,
      HttpServletResponse response, Object handler, Exception ex)
      throws Exception {
    // nothing to do
  }

  @Override
  public void postHandle(HttpServletRequest request,
      HttpServletResponse response, Object handler, ModelAndView modelAndView)
      throws Exception {
    if (modelAndView != null) {
      HttpSession session = request.getSession(true);

      modelAndView.addObject("menuItems", pageFrameworkService.getMenuItems());
      modelAndView.addObject("currentMenuItem",
          pageFrameworkService.getCurrentMenuItem(request));
      modelAndView.addObject("flashMessage",
          pageFrameworkService.getFlashMessage(session));

      if (pageFrameworkService.getIsRedirect(session) == false) {
        pageFrameworkService.setFlashMessage(session, null);
      }
    }
  }
}