[![Build](https://github.com/theborakompanioni/thymeleaf-extras-shiro/actions/workflows/build.yml/badge.svg)](https://github.com/theborakompanioni/thymeleaf-extras-shiro/actions/workflows/build.yml)
[![GitHub Release](https://img.shields.io/github/release/theborakompanioni/thymeleaf-extras-shiro.svg?maxAge=3600)](https://github.com/theborakompanioni/thymeleaf-extras-shiro/releases/latest)
[![Maven Central](https://img.shields.io/maven-central/v/com.github.theborakompanioni/thymeleaf-extras-shiro.svg?maxAge=3600)](http://search.maven.org/#search|ga|1|g%3A%22com.github.theborakompanioni%22%20AND%20a%3A%22thymeleaf-extras-shiro%22)
[![License](https://img.shields.io/github/license/theborakompanioni/thymeleaf-extras-shiro.svg?maxAge=2592000)](https://github.com/theborakompanioni/thymeleaf-extras-shiro/blob/master/LICENSE)

thymeleaf-extras-shiro
---

A [Thymeleaf](https://www.thymeleaf.org/) dialect for [Apache Shiro](https://shiro.apache.org) [tags](https://shiro.apache.org/tags).


## Download

##### Maven
```
<dependency>
    <groupId>com.github.theborakompanioni</groupId>
    <artifactId>thymeleaf-extras-shiro</artifactId>
    <version>${thymeleaf-shiro.version}</version>
</dependency>
```
##### Jar
[Download](https://search.maven.org/#search|gav|1|g%3A%22com.github.theborakompanioni%22%20AND%20a%3A%22thymeleaf-extras-shiro%22) from Maven Central.

##### Repository
```
git clone https://github.com/theborakompanioni/thymeleaf-extras-shiro.git
```

## Example
```html
<!DOCTYPE html>
<html xmlns:shiro="http://www.pollix.at/thymeleaf/shiro">

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
These are all implementations of the examples given in the [JSP / GSP Tag Library Section](https://shiro.apache.org/web.html#Web-JSP%252FGSPTagLibrary) of the Apache Shiro documentation.

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

#### The `hasAllRoles` tag
```html
<p shiro:hasAllRoles="developer, project manager">
  You are a developer and a project manager.
</p>
```

#### The `hasAnyRoles` tag
```html
<p shiro:hasAnyRoles="developer, project manager, administrator">
  You are a developer, project manager, or administrator.
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

#### The `hasAllPermissions` tag
```html
<p shiro:hasAllPermissions="user:create, user:delete">
  You can create and delete users.
</p>
```

#### The `hasAnyPermissions` tag
```html
<p shiro:hasAnyPermissions="user:create, user:delete">
  You can create or delete users.
</p>
```


## Resources
- Apache Shiro (Website): https://shiro.apache.org
- Apache Shiro (GitHub): https://github.com/apache/shiro
- Thymeleaf (Website): https://www.thymeleaf.org/
- Thymeleaf (GitHub): https://github.com/thymeleaf/thymeleaf

## License
The project is licensed under the Apache License. See [LICENSE](LICENSE) for details.
