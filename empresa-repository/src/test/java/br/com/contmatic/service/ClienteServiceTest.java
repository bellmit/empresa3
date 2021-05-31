package br.com.contmatic.service;

import static br.com.contmatic.easyRandom.EasyRandomClass.geraNomeUsuario;
import static org.joda.time.DateTime.now;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.contmatic.easyRandom.EasyRandomClass;
import br.com.contmatic.empresa.Cliente;

public class ClienteServiceTest {

    private ClienteService clienteService = new ClienteService();

    private static EasyRandomClass randomObject = EasyRandomClass.InstanciaEasyRandomClass();

    private static final Logger LOGGER = LoggerFactory.getLogger(ClienteServiceTest.class);

    @Test
    public void loop() {
        for(int i = 0 ; i < 0; i++) {
            deve_salvar_empresa();
        }
    }

    @Test
    public void deve_salvar_empresa() {
        try {
            Cliente cliente = randomObject.clienteRandomizer();
            clienteService.save(cliente);
            assertEquals(cliente.getNome(), clienteService.findById(cliente.getCpf()).getNome());
        } catch (Exception e) { 
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Test
    public void deve_atualizar_um_cliente() {
        try {
            Cliente cliente = randomObject.clienteRandomizer();
            clienteService.save(cliente);
            cliente.setNome("Atualizando um cliente");
            cliente.setDataModificacao(now());
            cliente.setUsuarioModificacao(geraNomeUsuario());
            clienteService.update(cliente);
            Cliente clienteBanco = clienteService.findById(cliente.getCpf());
            assertEquals("Atualizando um cliente", clienteBanco.getNome());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
    
    @Test
    public void deve_atualizar_um_cliente_pelo_campo_nome() {
        try {
            Cliente cliente = randomObject.clienteRandomizer();
            String nomeCliente = cliente.getNome();
            clienteService.save(cliente);
            cliente.setNome("Atualizando um cliente pelo campo");
            cliente.setDataModificacao(now());
            cliente.setUsuarioModificacao(geraNomeUsuario());
            clienteService.updateByField("nome", nomeCliente, cliente);
            Cliente clienteBanco = clienteService.findById(cliente.getCpf());
            assertEquals("Atualizando um cliente pelo campo", clienteBanco.getNome());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
    
    @Test
    public void deve_deletar_um_cliente_pelo_id() {
        try {
            Cliente cliente = randomObject.clienteRandomizer();
            clienteService.save(cliente);
            assertEquals(cliente.getNome(), clienteService.findById(cliente.getCpf()).getNome());
            clienteService.deleteById(cliente.getCpf());
            Cliente clienteDeletado = clienteService.findById(cliente.getCpf());
            assertEquals(null, clienteDeletado.getCpf());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
    
    @Test
    public void deve_deletar_um_cliente_pelo_campo() {
        try {
            Cliente cliente = randomObject.clienteRandomizer();
            String nome = cliente.getNome();
            clienteService.save(cliente);
            assertEquals(cliente.getNome(), clienteService.findById(cliente.getCpf()).getNome());
            clienteService.deleteByField("nome", nome, cliente);
            Cliente clienteDeletado = clienteService.findById(cliente.getCpf());
            assertEquals(null, clienteDeletado.getCpf());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
    
    @Test
    public void deve_selecioar_um_cliente_pelo_id() {
        try {
            Cliente cliente = randomObject.clienteRandomizer();
            clienteService.save(cliente);
            Cliente clienteBanco = clienteService.findById(cliente.getCpf());
            assertEquals(cliente.getNome(), clienteBanco.getNome());
            clienteService.deleteById(cliente.getCpf());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
    
    @Test
    public void deve_selecionar_nome() {
        try {
            Cliente cliente = randomObject.clienteRandomizer();
            clienteService.save(cliente);
            List<String> campos = new ArrayList<>();
            Collections.addAll(campos, "nome");
            Cliente clienteBuscado = clienteService.findAndReturnsSelectedFields("_id", cliente.getCpf(), campos);
            assertEquals(cliente.getNome(), clienteBuscado.getNome());
            clienteService.deleteById(cliente.getCpf());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
    
    @Test
    public void deve_selecionar_nome_email() {
        try {
            Cliente cliente = randomObject.clienteRandomizer();
            clienteService.save(cliente);
            List<String> campos = new ArrayList<>();
            Collections.addAll(campos, "nome", "email");
            Cliente clienteBuscado = clienteService.findAndReturnsSelectedFields("_id", cliente.getCpf(), campos);
            assertEquals(cliente.getNome(), clienteBuscado.getNome());
            clienteService.deleteById(cliente.getCpf());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
    
    @Test
    public void deve_selecionar_nome_email_boleto() {
        try {
            Cliente cliente = randomObject.clienteRandomizer();
            clienteService.save(cliente);
            List<String> campos = new ArrayList<>();
            Collections.addAll(campos, "nome", "email", "boleto");
            Cliente clienteBuscado = clienteService.findAndReturnsSelectedFields("_id", cliente.getCpf(), campos);
            assertEquals(cliente.getNome(), clienteBuscado.getNome());
            clienteService.deleteById(cliente.getCpf());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
    
    @Test
    public void deve_selecioar_todos_os_clientes() {
        try {
            List<Cliente> clientesBanco = clienteService.findAll();
            assertNotNull(clientesBanco);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

}
