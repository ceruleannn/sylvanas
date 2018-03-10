/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package javax.servlet;

/**
 * 
 * TODO SERVLET3 - Add comments
 * @since Servlet 3.0
 */
public interface SessionCookieConfig {
    
    /**
     * 
     * @param name
     * @throws IllegalStateException
     */
    void setName(String name);
    
    String getName();
    
    /**
     * 
     * @param domain
     * @throws IllegalStateException
     */
    void setDomain(String domain);
    
    String getDomain();
    
    /**
     * 
     * @param path
     * @throws IllegalStateException
     */
    void setPath(String path);
    
    String getPath();
    
    /**
     * 
     * @param comment
     * @throws IllegalStateException
     */
    void setComment(String comment);
    
    String getComment();
    
    /**
     * 
     * @param httpOnly
     * @throws IllegalStateException
     */
    void setHttpOnly(boolean httpOnly);
    
    boolean isHttpOnly();
    
    /**
     * 
     * @param secure
     * @throws IllegalStateException
     */
    void setSecure(boolean secure);
    
    boolean isSecure();

    /**
     * Sets the maximum age.
     * 
     * @param MaxAge the maximum age to set
     * @throws IllegalStateException
     */
    void setMaxAge(int MaxAge);
    
    int getMaxAge();
    
}
