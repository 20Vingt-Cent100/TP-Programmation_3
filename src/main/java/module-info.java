module ca.qc.bdeb.sim.tp2_camelotavelo {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.compiler;


    opens ca.qc.bdeb.sim.tp2_camelotavelo to javafx.fxml;
    exports ca.qc.bdeb.sim.tp2_camelotavelo;
}