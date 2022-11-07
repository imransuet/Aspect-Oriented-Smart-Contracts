package roleobject;

import roleobject.EmployeeRole;

public interface Employee {

    public abstract String getName();
    public abstract String getId();
    public abstract EmployeeRole getRole(String aSpec);
    public abstract void addRole(String aSpec, EmployeeRole emp);
    public abstract void removeRole(String aSpec);
    public abstract boolean hasRole(String aSpec);


}
