package it.marche.regione.pddnica.client;

public enum PrestazioneDaErogare {
	A101("A1.01"),
	A102("A1.02"),
	A103("A1.03"),
	A104("A1.04"),
	A105("A1.05"),
	A106("A1.06"),
	A107("A1.07"),
	A108("A1.08"),
	A109("A1.09"),
	A110("A1.10"),
	A111("A1.11"),
	A112("A1.12"),
	A113("A1.13"),
	A114("A1.14"),
	A115("A1.15"),
	A116("A1.16"),
	A117("A1.17"),
	A118("A1.18"),
	A119("A1.19"),
	A120("A1.20"),
	A201("A2.01"),
	A202("A2.02"),
	A203("A2.03"),
	A204("A2.04"),
	A205("A2.05"),
	A206("A2.06"),
	A207("A2.07"),
	A208("A2.08"),
	A209("A2.09"),
	A210("A2.10"),
	A211("A2.11"),
	A212("A2.12"),
	A301("A3.01"),
	A302("A3.02"),
	A303("A3.03"),
	Z999("Z9.99"); 
	
	 private final String text;
    
	PrestazioneDaErogare(final String text) {
        this.text = text;
    }

    
    @Override
    public String toString() {
        return text;
    }
}
