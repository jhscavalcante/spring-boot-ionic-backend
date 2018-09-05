package com.nelioalves.cursomc.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.nelioalves.cursomc.domain.PagamentoComBoleto;

@Service
public class BoletoService {
	
	private final int DIAS_A_MAIS_DO_VENCIMENTO = 7;

	public void preencherPagamentoComBoleto(PagamentoComBoleto pagto, Date instanteDoPedido) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(instanteDoPedido);
		cal.add(Calendar.DAY_OF_MONTH, DIAS_A_MAIS_DO_VENCIMENTO); // acrescenta 7 dias apóa a Data do Pedido
		pagto.setDataVencimento(cal.getTime());
	}
}
