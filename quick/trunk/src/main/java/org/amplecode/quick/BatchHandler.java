package org.amplecode.quick;

/*
 * Copyright (c) 2008, the original author or authors.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the AmpleCode project nor the names of its
 *       contributors may be used to endorse or promote products derived from
 *       this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

import java.util.Collection;

/**
 * Interface responsible for performing batch and regular JDBC operations. Batch 
 * insert operations can be achieved with the addObject( Object ) method, which 
 * will utilize multiple insert SQL statements for high performance.
 * 
 * @author Lars Helge Overland
 * @version $Id$
 */
public interface BatchHandler<T>
{
    /**
     * Initializes the BatchHandler by acquiring a database connection, creating a
     * statement object and initializing a SQL statement.
     */
    BatchHandler<T> init();
    
    /**
     * Returns the current JdbcConfiguration.
     */
    JdbcConfiguration getConfiguration();
    
    /**
     * Used to set table name for generic BatchHandlers, a typical implementation
     * will set this property itself.
     * 
     * @param name the name of the database table.
     */
    BatchHandler<T> setTableName( String name );
    
    /**
     * Adds an object to the BatchHandler.
     * 
     * @param object the object to add.
     */
    void addObject( T object );
    
    /**
     * Inserts an object directly.
     * 
     * @param object the object to insert.
     * @param returnGeneratedIdentifier return generated indentifier or not.
     * @return the generated identifier if returnGeneratedIdentifier is true and
     *         the underlying table has an auto-increment identifier, 0 elsewise.
     */
    int insertObject( T object, boolean returnGeneratedIdentifier );
    
    /**
     * Updates an object.
     * 
     * @param object the object to update.
     */
    void updateObject( T object );
    
    /**
     * Deletes an object.
     * 
     * @param object the object to delete.
     */
    void deleteObject( T object );
    
    /**
     * Checks whether this object exists in the database or not.
     * 
     * @param object the object to check.
     * @return true if the object exists, false if not.
     */
    boolean objectExists( T object );
    
    /**
     * Returns the identifier of the object with the given name.
     * 
     * @param object the value to uniquely identify the object to get the identifier from.
     * @return the identifier of the object with the given name, 0 if object does not exist.
     */
    int getObjectIdentifier( Object object );
    
    /**
     * Flushes the BatchHandler by executing a potential remaining statement, and
     * closing the statement object and the database connection.
     * 
     * @return a Collection of the generated identifiers for objects with auto incrementing
     *         identifiers.
     */
    Collection<Integer> flush();
}
