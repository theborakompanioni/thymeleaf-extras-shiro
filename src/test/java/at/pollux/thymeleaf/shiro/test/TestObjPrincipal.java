/*
 * Copyright 2013 Art Gramlich.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package at.pollux.thymeleaf.shiro.test;

/**
 * @author art
 */
public class TestObjPrincipal {

    private String name;

    public TestObjPrincipal(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
