package roleobject;

public abstract class EmployeeRole implements Employee {

    protected EmployeeCore empCore;

    protected final void setCore(EmployeeCore core)
    {

        this.empCore= core;
    }

    @Override
    public String getName() {
        return empCore.getName();
    }

    @Override
    public String getId() {
        return empCore.getId();
    }

    @Override
    public EmployeeRole getRole(String aSpec) {
        return empCore.getRole(aSpec);
    }

    @Override
    public void addRole(String aSpec, EmployeeRole emp) {
        empCore.addRole(aSpec,emp);
    }

    @Override
    public void removeRole(String aSpec) {
        empCore.removeRole(aSpec);
    }

    @Override
    public boolean hasRole(String aSpec) {
        return empCore.hasRole(aSpec);
    }
}
