package roleobject;

import roleobject.Employee;
import roleobject.EmployeeRole;

import java.util.HashMap;

public class EmployeeCore implements Employee {

    private String name;
    private String id;

    public EmployeeCore(String name, String id)
    {
        this.name = name;
        this.id = id;
    }


    private HashMap<String, EmployeeRole> roles = new HashMap<String, EmployeeRole>();

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public EmployeeRole getRole(String aSpec) {
        return roles.get(aSpec);
    }

    @Override
    public void addRole(String aSpec, EmployeeRole emp) {

        emp.setCore(this);
        roles.put(aSpec,emp);
    }

    @Override
    public void removeRole(String aSpec) {
        roles.remove(aSpec);
    }

    @Override
    public boolean hasRole(String aSpec) {
        return roles.containsKey(aSpec);
    }
}
