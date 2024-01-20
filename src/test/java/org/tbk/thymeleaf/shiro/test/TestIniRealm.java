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
package org.tbk.thymeleaf.shiro.test;

import org.apache.shiro.authc.SimpleAccount;
import org.apache.shiro.config.Ini;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.SimplePrincipalCollection;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author art
 */
public class TestIniRealm extends IniRealm {

    private final AtomicInteger counter = new AtomicInteger();

    public TestIniRealm() {
    }

    public TestIniRealm(String resourcePath) {
        super(resourcePath);
    }

    public TestIniRealm(Ini ini) {
        super(ini);
    }

    @Override
    protected void add(SimpleAccount account) {
        String username = (String) account.getPrincipals().getPrimaryPrincipal();

        // Let's add some additional principals for testing
        SimplePrincipalCollection principalCollection = new SimplePrincipalCollection();
        principalCollection.addAll(account.getPrincipals());
        principalCollection.add(counter.getAndIncrement(), "integerRealm");
        TestObjPrincipal objPrinc = new TestObjPrincipal(username.toUpperCase() + " " + username.toUpperCase());
        principalCollection.add(objPrinc, "objRealm");
        account.setPrincipals(principalCollection);

        super.add(account);
    }
}
