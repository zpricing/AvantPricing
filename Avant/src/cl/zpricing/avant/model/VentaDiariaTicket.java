package cl.zpricing.avant.model;

public class VentaDiariaTicket extends VentaDiaria {
	private Ticket ticket;

	public Ticket getTicket() {
		return ticket;
	}
	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}
}
