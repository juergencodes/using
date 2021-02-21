```
ansible -i ./inventory/hosts vm -m ping --user juergen --ask-pass
```

```
ansible-playbook ./playbooks/vm.yml -i ./inventory/hosts --ask-become-pass
```