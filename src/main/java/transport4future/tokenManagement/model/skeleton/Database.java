/*
 * Copyright (c) 2020.
 * Content created by:
 * - Andrei García Cuadra
 * - Miguel Hernández Cassel
 *
 * For the module PDS, on university Carlos III de Madrid.
 * Do not share, review nor edit any content without implicitly asking permission to it's owners, as you can contact by this email:
 * andreigarciacuadra@gmail.com
 *
 * All rights reserved.
 */

package Transport4Future.TokenManagement.model.skeleton;

import Transport4Future.TokenManagement.config.Constants;
import Transport4Future.TokenManagement.exception.TokenManagementException;
import Transport4Future.TokenManagement.service.FileManager;

/**
 * The type Database.
 *
 * @param <L> the type parameter
 */
public abstract class Database<L> {

    /**
     * Instantiates a new Database.
     */
    protected Database() {
        FileManager fileManager = new FileManager();
        fileManager.createPathRecursive(Constants.STORAGE_PATH);
    }

    /**
     * Add.
     *
     * @param obj the obj
     * @throws TokenManagementException the token management exception
     */
    public abstract void add(L obj) throws TokenManagementException;

    /**
     * Save.
     *
     * @throws TokenManagementException the token management exception
     */
    protected abstract void save() throws TokenManagementException;

    /**
     * Reload.
     *
     * @throws TokenManagementException the token management exception
     */
    protected abstract void reload() throws TokenManagementException;

    /**
     * Init database.
     */
    protected abstract void initDatabase();
}
