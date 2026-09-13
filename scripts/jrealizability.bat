@setlocal
@rem Parsing and the AST visitors recurse on expression structure, and generated
@rem Lustre can nest very deeply, so the default stack is not always enough.
@rem Override by setting JKIND_JAVA_OPTS.
@if "%JKIND_JAVA_OPTS%"=="" set JKIND_JAVA_OPTS=-Xss32m
@java %JKIND_JAVA_OPTS% -jar "%~dp0jkind.jar" -jrealizability %*
