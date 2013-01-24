See [http://shiro.apache.org/tags](http://shiro.apache.org/tags)

## The `guest` tag
    <shiro:guest>
        Hi there! Please <a href="login.jsp">Login</a> or <a href="signup.jsp">Signup</a> today!
    </shiro:guest>

turns into:

    <div shiro:guest="">
        Hi there! Please <a href="login.jsp">Login</a> or <a href="signup.jsp">Signup</a> today!
    </div>

## The `user` tag
    <shiro:user>
        Welcome back John!  Not John? Click <a href="login.jsp">here<a> to login.
    </shiro:user>

turns to:

    <div shiro:user="">
        Welcome back John!  Not John? Click <a href="login.jsp">here<a> to login.
    </div>

## The `authenticated` tag
    <shiro:authenticated>
        <a href="updateAccount.jsp">Update your contact information</a>.
    </shiro:authenticated>

turns to:

    <div shiro:authenticated="">
        <a href="updateAccount.jsp">Update your contact information</a>.
    </div>

## The `notAuthenticated` tag
    <shiro:notAuthenticated>
        Please <a href="login.jsp">login</a> in order to update your credit card information.
    </shiro:notAuthenticated>

turns to:

    <div shiro:notAuthenticated="">
        Please <a href="login.jsp">login</a> in order to update your credit card information.
    </div>

## The `principal` tag
    <div>Hello, <shiro:principal/>, how are you today?</div>

turns to:

    <div>Hello, <span shiro:principal="" />, how are you today?</div>

**Typed principal and principal property is not supported yet**

## The `hasRole` tag
    <shiro:hasRole name="administrator">
        <a href="admin.jsp">Administer the system</a>
    </shiro:hasRole>

turns to:

    <div shiro:hasRole="administrator">
        <a href="admin.jsp">Administer the system</a>
    </div>

## The `lacksRole` tag
    <shiro:lacksRole name="administrator">
        Sorry, you are not allowed to administer the system.
    </shiro:lacksRole>

turns to:

    <div shiro:lacksRole="administrator">
        Sorry, you are not allowed to administer the system.
    </div>

## The `hasAnyRoles` tag
    <shiro:hasAnyRoles name="developer, project manager, administrator">
        You are either a developer, project manager, or administrator.
    </shiro:lacksRole>

turns to:

    <div shiro:hasAnyRoles="developer, project manager, administrator">
        You are either a developer, project manager, or administrator.
    </div>

## The `hasPermission` tag
    <shiro:hasPermission name="user:create">
        <a href="createUser.jsp">Create a new User</a>
    </shiro:hasPermission>

turns to:

    <div shiro:hasPermission="user:create">
        <a href="createUser.jsp">Create a new User</a>
    </div>

## The `lacksPermission` tag
    <shiro:lacksPermission name="user:delete">
        Sorry, you are not allowed to delete user accounts.
    </shiro:hasPermission>

turns to:

    <div shiro:lacksPermission="user:delete">
        Sorry, you are not allowed to delete user accounts.
    </div>
