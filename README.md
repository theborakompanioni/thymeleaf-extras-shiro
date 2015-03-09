thymeleaf-extras-shiro
---

A [Thymeleaf](http://www.thymeleaf.org/) dialect for [Apache Shiro](https://shiro.apache.org) [tags](https://shiro.apache.org/tags).


## Download

##### Maven
```
<dependency>
    <groupId>com.github.theborakompanioni</groupId>
    <artifactId>thymeleaf-extras-shiro</artifactId>
    <version>1.1.0</version>
</dependency>
```
##### Jar
[Download](http://search.maven.org/#search|gav|1|g%3A%22com.github.theborakompanioni%22%20AND%20a%3A%22thymeleaf-extras-shiro%22) from Maven Central.

##### Repository
```
git clone https://github.com/theborakompanioni/thymeleaf-extras-shiro.git
```

## Example
```html
<!DOCTYPE html xmlns:shiro="http://www.pollix.at/thymeleaf/shiro">
<html>

  <head>
    <title>thymeleaf-extras-shiro</title>
  </head>

  <body>
    <p shiro:guest="">Please <a href="login.html">login</a></p>
    <p shiro:authenticated="">
      Hello, <span shiro:principal=""></span>, how are you today?
    </p>
  </body>

</html>
```

### Tags

The following examples show how to integrate the tags in your Thymeleaf templates.
These are all implementations of the examples given in the [JSP / GSP Tag Library Section](http://shiro.apache.org/web.html#Web-JSP%252FGSPTagLibrary) of the Apache Shiro documentation.

Tags can be written in attribute or element notation:

##### Attribute
```html
<p shiro:anyTag>
  Goodbye cruel World!
</p>
```

##### Element
```html
<shiro:anyTag>
  <p>Hello World!</p>
</shiro:anyTag>
```

* * * 

#### The `guest` tag
```html
<p shiro:guest="">
  Please <a href="login.html">Login</a>
</p>
```

#### The `user` tag
```html
<p shiro:user="">
  Welcome back John! Not John? Click <a href="login.html">here<a> to login.
</p>
```

#### The `authenticated` tag
```html
<a shiro:authenticated="" href="updateAccount.html">Update your contact information</a>
```

#### The `notAuthenticated` tag
```html
<p shiro:notAuthenticated="">
  Please <a href="login.html">login</a> in order to update your credit card information.
</p>
```

#### The `principal` tag
```html
<p>Hello, <span shiro:principal=""></span>, how are you today?</p>
```
or
```html
<p>Hello, <shiro:principal/>, how are you today?</p>
```

Typed principal and principal property are also supported.

#### The `hasRole` tag
```html
<a shiro:hasRole="administrator" href="admin.html">Administer the system</a>
```

#### The `lacksRole` tag
```html
<p shiro:lacksRole="administrator">
  Sorry, you are not allowed to administer the system.
</p>
```

#### The `hasAnyRoles` tag
```html
<p shiro:hasAnyRoles="developer, project manager, administrator" class="message">
  You are either a developer, project manager, or administrator.
</p>
```

#### The `hasPermission` tag
```html
<a shiro:hasPermission="user:create" href="createUser.html">Create a new User</a>
```

#### The `lacksPermission` tag
```html
<p shiro:lacksPermission="user:delete">
  Sorry, you are not allowed to delete user accounts.
</p>
```

License
-------

The project is licensed under the Apache license. See
[LICENSE](https://github.com/theborakompanioni/thymeleaf-extras-shiro/blob/master/LICENSE.md) for details.
