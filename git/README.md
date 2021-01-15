# Gespeichertes Passwort löschen

Windows+R

```
control /name Microsoft.CredentialManager
```

# Datum des letzten commits auf JETZT setzen

```
git commit --amend --no-edit --date "$(date)"
```

# Autor (+ Datum) des letzten commits neu setzen

```
git commit --amend --reset-author --no-edit
```

# Beispiel .gitignore mit typischen Einträgen

```
target

*.iml
.idea

.settings
.project
.classpath

.gradle
gradle
build
```