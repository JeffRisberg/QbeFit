package com.incra.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.incra.domain.LogEntry;
import com.incra.services.LogEntryService;
import com.incra.services.PageFrameworkService;

/**
 * The <i>LogEntryController</i> controller defines operations on logEntry,
 * including selection.
 * 
 * @author Jeffrey Risberg
 * @since 01/22/11
 */
@Controller
public class LogEntryController {

    protected static Logger logger = LoggerFactory.getLogger(LogEntryController.class);

    @Autowired
    private LogEntryService logEntryService;
    @Autowired
    private PageFrameworkService pageFrameworkService;

    public LogEntryController() {
    }

    @RequestMapping(value = "/logEntry/**")
    public String index() {
        return "redirect:/logEntry/list";
    }

    @RequestMapping(value = "/logEntry/list")
    public ModelAndView list(Object criteria) {

        List<LogEntry> logEntryList = logEntryService.findEntityList();

        ModelAndView modelAndView = new ModelAndView("logEntry/list");
        modelAndView.addObject("logEntryList", logEntryList);
        return modelAndView;
    }

    @RequestMapping(value = "/logEntry/show/{id}", method = RequestMethod.GET)
    public String show(@PathVariable int id, Model model, HttpSession session) {

        LogEntry logEntry = logEntryService.findEntityById(id);
        if (logEntry != null) {
            model.addAttribute(logEntry);
            return "logEntry/show";
        } else {
            pageFrameworkService.setFlashMessage(session, "No LogEntry with that id");
            pageFrameworkService.setIsRedirect(session, Boolean.TRUE);
            return "redirect:/logEntry/list";
        }
    }
}