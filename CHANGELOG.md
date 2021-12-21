# Change Log
All notable changes to this project will be documented in this file.
This project adheres to [Semantic Versioning](https://semver.org/).

## [Unreleased][unreleased]
- Updated Thymeleaf version from 3.0.12.RELEASE to 3.0.14.RELEASE
- Updated Shiro version from 1.7.1 to 1.8.0

## [2.1.0] - 2021-08-11
### Updated
- Updated Thymeleaf version from 3.0.2.RELEASE to 3.0.12.RELEASE
- Updated Shiro version from 1.3.2 to 1.7.1

## [2.0.0] - 2016-10-11
### Added
- added support for expression evaluation, e.g:
```
<div shiro:hasAllPermissions="${permissions}">
   You have all necessary permissions.
</div>
```

### Updated
- Updated Thymeleaf version from 2.1.4 to 3.0.2.RELEASE
- Updated Shiro version from 1.2.4 to 1.3.2


## [1.2.1] - 2015-09-07
### Fixed
- Problems with v1.2.0 in Maven Central


## [1.2.0] - 2015-09-06
### Added
- Added support for `hasAllRoles`, `hasAllPermissions` and `hasAnyPermissions`
### Updated
- Updated Thymeleaf version from 2.1.0 to 2.1.4
- Updated Shiro version from 1.2.2 to 1.2.4


[unreleased]: https://github.com/theborakompanioni/thymeleaf-extras-shiro/compare/2.1.0...HEAD
[2.1.0]: https://github.com/theborakompanioni/thymeleaf-extras-shiro/compare/2.1.0...2.0.0
[2.0.0]: https://github.com/theborakompanioni/thymeleaf-extras-shiro/compare/1.2.1...2.0.0
[1.2.1]: https://github.com/theborakompanioni/thymeleaf-extras-shiro/compare/1.2.0...1.2.1
[1.2.0]: https://github.com/theborakompanioni/thymeleaf-extras-shiro/compare/74596f4...1.2.0
