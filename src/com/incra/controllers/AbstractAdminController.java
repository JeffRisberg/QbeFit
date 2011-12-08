package com.incra.controllers;

import org.springframework.security.access.annotation.Secured;

/**
 * The <i>AbstractAdminController</i> provides utility functions to all
 * controllers which are carrying out admin CRUD operations.
 * 
 * @author Jeffrey Risberg
 * @since 12/05/11
 */
@Secured("ROLE_ADMIN")
public abstract class AbstractAdminController {

    // to be filled in
}
