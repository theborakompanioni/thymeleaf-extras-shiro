Shiro Tags Usage With Thymeleaf
---
See [http://shiro.apache.org/tags](http://shiro.apache.org/tags) and
[http://shiro.apache.org/web.html#Web-JSP%252FGSPTagLibrary](http://shiro.apache.org/web.html)
* * *

### The `guest` tag
    <shiro:guest>
        <p>Please <a href="login.html">Login</a></p>
    </shiro:guest>

preferred approach:

    <p shiro:guest="">Please <a href="login.html">Login</a></p>

### The `user` tag
    <shiro:user>
        <div>Welcome back John!  Not John? Click <a href="login.html">here<a> to login.</div>
    </shiro:user>

preferred approach:

    <div shiro:user="">
        Welcome back John!  Not John? Click <a href="login.html">here<a> to login.
    </div>

### The `authenticated` tag
    <shiro:authenticated>
        <a href="updateAccount.html">Update your contact information</a>.
    </shiro:authenticated>

preferred approach:

    <a shiro:authenticated="" href="updateAccount.html">Update your contact information</a>

### The `notAuthenticated` tag
    <shiro:notAuthenticated>
        <p>Please <a href="login.html">login</a> in order to update your credit card information.</p>
    </shiro:notAuthenticated>

preferred approach:

    <p shiro:notAuthenticated="">
        Please <a href="login.html">login</a> in order to update your credit card information.
    </p>

### The `principal` tag
    <p>Hello, <shiro:principal/>, how are you today?</p>

preferred approach:

    <p>Hello, <span shiro:principal="" />, how are you today?</p>

**Typed principal and principal property are also supported**

### The `hasRole` tag
    <shiro:hasRole name="administrator">
        <a href="admin.html">Administer the system</a>
    </shiro:hasRole>

preferred approach:

    <a shiro:hasRole="administrator" href="admin.html">Administer the system</a>

### The `lacksRole` tag
    <shiro:lacksRole name="administrator">
        <p>Sorry, you are not allowed to administer the system.</p>
    </shiro:lacksRole>

preferred approach:

    <p shiro:lacksRole="administrator">
        Sorry, you are not allowed to administer the system.
    </p>

### The `hasAnyRoles` tag
    <shiro:hasAnyRoles name="developer, project manager, administrator">
        <div class="message">You are either a developer, project manager, or administrator.</div>
    </shiro:lacksRole>

preferred approach:

    <div shiro:hasAnyRoles="developer, project manager, administrator" class="message">
        You are either a developer, project manager, or administrator.
    </div>

### The `hasPermission` tag
    <shiro:hasPermission name="user:create">
        <a href="createUser.html">Create a new User</a>
    </shiro:hasPermission>

preferred approach:

    <a shiro:hasPermission="user:create" href="createUser.html">Create a new User</a>

### The `lacksPermission` tag
    <shiro:lacksPermission name="user:delete">
        <p>Sorry, you are not allowed to delete user accounts.</p>
    </shiro:hasPermission>

preferred approach:

    <p shiro:lacksPermission="user:delete">Sorry, you are not allowed to delete user accounts.</p>
