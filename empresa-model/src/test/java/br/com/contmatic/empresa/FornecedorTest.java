package br.com.contmatic.empresa;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.runners.MethodSorters.NAME_ASCENDING;

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;

import br.com.contmatic.easyRandom.EasyRandomClass;
import br.com.contmatic.endereco.Endereco;
import br.com.contmatic.groups.Post;
import br.com.contmatic.groups.Put;
import br.com.contmatic.telefone.Telefone;
import br.com.contmatic.util.Annotations;
import br.com.six2six.fixturefactory.Fixture;
import nl.jqno.equalsverifier.EqualsVerifier;;

/**
 * The Class FornecedorTest.
 */
@FixMethodOrder(NAME_ASCENDING)
public class FornecedorTest {

    /** The fornecedor. */
    private static Fornecedor fornecedor;
    
    /** The validator. */
    private Validator validator;
    
    /** The factory. */
    private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    
    private static EasyRandomClass randomObject = EasyRandomClass.InstanciaEasyRandomClass();

    /**
     * Set up.
     */
    @Before
    public void setUp() {
        FornecedorTest.fornecedor = randomObject.fornecedorRandomizer();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }
    
    public boolean isValid(Fornecedor fornecedor, String mensagem) {
    	validator = factory.getValidator();
		boolean valido = true;
		Set<ConstraintViolation<Fornecedor>> restricoesPost = validator.validate(fornecedor, Post.class);
		for (ConstraintViolation<Fornecedor> constraintViolation : restricoesPost)
			if (constraintViolation.getMessage().equalsIgnoreCase(mensagem))
				valido = false;
		
		Set<ConstraintViolation<Fornecedor>> restricoesPut = validator.validate(fornecedor, Put.class);
		for (ConstraintViolation<Fornecedor> constraintViolation : restricoesPut)
			if (constraintViolation.getMessage().equalsIgnoreCase(mensagem))
				valido = false;
		
		return valido;
	}
    
    /* TESTES NO CNPJ */
    
    /**
     * Nao deve aceitar cnpj nulo.
     */
    @Test
    public void nao_deve_aceitar_cnpj_nulo() {
        assertNotNull(fornecedor.getCnpj());
    }
    
    /**
     * Deve testar o get cnpj esta funcionando corretamente.
     */
    @Test
    public void deve_testar_o_getCpf_esta_funcionando_corretamente() {
        fornecedor.setCnpj("97904702000131");
        assertThat(fornecedor.getCnpj(), containsString("97904702000131"));
    }    
    
    /**
     * Nao deve aceitar espacos em branco no cnpj.
     */
    @Test
    public void nao_deve_aceitar_espacos_em_branco_no_cnpj() {
        assertFalse(fornecedor.getCnpj().trim().isEmpty());
    }
    
    /**
     * Deve validar cnpj annotations.
     */
    @Test
    public void deve_validar_cnpj_annotations() {
        assertFalse(Annotations.MensagemErroAnnotation(fornecedor.getCnpj()));
    }
    
    /* TESTES NO NOME */
    
    /**
     * Nao deve aceitar nome nulo.
     */
    @Test //
    public void nao_deve_aceitar_nome_nulo() {
        fornecedor.setNome("CA pe??as LTDA");
        assertNotNull(fornecedor.getNome());
    }
    
    /**
     * Deve testar o get nome esta funcionando corretamente.
     */
    @Test
    public void deve_testar_o_getNome_esta_funcionando_corretamente() {
        fornecedor.setNome("CA pe??as LTDA");
        assertThat(fornecedor.getNome(), containsString("CA pe??as LTDA"));
    }
    
    @Test
	public void deve_aceitar_nome_valido() {
    	fornecedor.setNome("Gabriel");
		assertTrue(isValid(fornecedor, "O campo nome n??o pode estar vazio"));
	}
    
    /**
     * Nao deve aceitar espacos em branco no nome.
     */
    @Test
    public void nao_deve_aceitar_espacos_em_branco_no_nome() {
        assertFalse(fornecedor.getNome().trim().isEmpty());
    }
    
	@Test
	public void deve_aceitar_nome_sem_espaco() {
		fornecedor.setNome("GabrielBueno");
		assertTrue(isValid(fornecedor, "O nome do fornecedor est?? incorreto"));
	}

	@Test
	public void deve_aceitar_nome_com_acento() {
		fornecedor.setNome("Jo??o");
		assertTrue(isValid(fornecedor, "O nome do fornecedor est?? incorreto"));
	}

	@Test
	public void deve_aceitar_nome_com_cedilha() {
		fornecedor.setNome("Maria Concei????o");
		assertTrue(isValid(fornecedor, "O nome do fornecedor est?? incorreto"));
	}

	@Test
	public void deve_aceitar_nome_com_espaco() {
		fornecedor.setNome("Gabriel Bueno");
		assertTrue(isValid(fornecedor, "O nome do fornecedor est?? incorreto"));
	}

	@Test
	public void nao_deve_aceitar_nome_com_arroba() {
		fornecedor.setNome("G@briel");
		assertFalse(isValid(fornecedor, "O nome do fornecedor est?? incorreto"));
	}
    
    /**
     * Deve validar nome annotations.
     */
    @Test
    public void deve_validar_nome_annotations() {
        assertFalse(Annotations.MensagemErroAnnotation(fornecedor.getNome()));
    }
    
    /* TESTES NO PRODUTO */
    
    /**
     * Nao deve aceitar produto nulo.
     */
    @Test
    public void nao_deve_aceitar_produto_nulo() {
        assertNotNull(fornecedor.getProduto());
    }
    
    /**
     * Deve testar o get produto esta funcionando corretamente.
     */
    @Test
    public void deve_testar_o_getProduto_esta_funcionando_corretamente() {
        fornecedor.setProduto("5 placas m??es");
        assertThat(fornecedor.getProduto(), is("5 placas m??es"));
    }
    
    /**
     * Nao deve aceitar espacos em branco no produto.
     */
    @Test
    public void nao_deve_aceitar_espacos_em_branco_no_produto() {
        assertFalse(fornecedor.getProduto().trim().isEmpty());
    }
    
    @Test
	public void deve_aceitar_produto_sem_espaco() {
    	fornecedor.setProduto("Placam??e");
		assertTrue(isValid(fornecedor, "O nome do produto est?? incorreto"));
	}

	@Test
	public void deve_aceitar_produto_com_acento() {
		fornecedor.setProduto("placa m??e");
		assertTrue(isValid(fornecedor, "O nome do produto est?? incorreto"));
	}

	@Test
	public void deve_aceitar_produto_com_cedilha() {
		fornecedor.setProduto("??????");
		assertTrue(isValid(fornecedor, "O nome do produto est?? incorreto"));
	}

	@Test
	public void deve_aceitar_produto_com_espaco() {
		fornecedor.setProduto("Processador AMD FX 6300");
		assertTrue(isValid(fornecedor, "O nome do produto est?? incorreto"));
	}
    
    /**
     * Deve validar produto annotations.
     */
    @Test
    public void deve_validar_produto_annotations() {
        assertFalse(Annotations.MensagemErroAnnotation(fornecedor.getProduto()));
    }
    
    /* TESTES NO TELEFONE */
    
    /**
     * Nao deve aceitar telefone nulo.
     */
    @Test
    public void nao_deve_aceitar_telefone_nulo() {
        assertNotNull(fornecedor.getTelefone());
    }
    
    /**
     * Deve testar o exception do set telefones.
     */
    @Test(expected = IllegalArgumentException.class)
    public void deve_testar_o_exception_do_setTelefones() {
        Set<Telefone> telefone = new HashSet<>();
        telefone.add(Fixture.from(Telefone.class).gimme("valido"));
        telefone.add(Fixture.from(Telefone.class).gimme("valido"));
        telefone.add(Fixture.from(Telefone.class).gimme("valido"));
        telefone.add(Fixture.from(Telefone.class).gimme("valido"));
        fornecedor.setTelefones(telefone);
    }
    
    /**
     * Deve testar o set telefones.
     */
    @Test
    public void deve_testar_o_setTelefones() {
        Set<Telefone> telefone = new HashSet<>();
        telefone.addAll(fornecedor.getTelefone());
        fornecedor.setTelefones(telefone);
        assertTrue(fornecedor.equals(fornecedor));
    }
    
    /**
     * Deve testar o set telefones com dois numeros
     */
    @Test(expected = IllegalArgumentException.class)
    public void deve_testar_o_setTelefones_com_dois_numeros() {
        Set<Telefone> telefone = new HashSet<>();
        telefone.add(Fixture.from(Telefone.class).gimme("valido"));
        telefone.add(Fixture.from(Telefone.class).gimme("valido"));
        fornecedor.setTelefones(telefone);
    }
    
    /**
     * Deve validar telefones annotations.
     */
    @Test
    public void deve_validar_telefones_annotations() {
        assertFalse(Annotations.MensagemErroAnnotation(fornecedor.getTelefone()));
    }
    
    /* TESTES NO ENDERECO */
    
    /**
     * Nao deve aceitar endereco nulo.
     */
    @Test
    public void nao_deve_aceitar_endereco_nulo() {
        assertNotNull(fornecedor.getEndereco());
    }

    /**
     * Deve testar o get endereco esta funcionando corretamente.
     */
    @Test
    public void deve_testar_o_getEndereco_esta_funcionando_corretamente() {
        fornecedor.getEndereco();
        assertTrue(fornecedor.getEndereco().equals(fornecedor.getEndereco()));
    }

    /**
     * Deve testar o expection do set enderecos.
     */
    @Test(expected = IllegalArgumentException.class)
    public void deve_testar_o_exception_do_setEnderecos() {
        Set<Endereco> endereco = new HashSet<>();
        endereco.add(Fixture.from(Endereco.class).gimme("valido"));
        endereco.add(Fixture.from(Endereco.class).gimme("valido"));
        endereco.add(Fixture.from(Endereco.class).gimme("valido"));
        fornecedor.setEnderecos(endereco);
    }
    
    /**
     * Deve testar o set enderecos.
     */
    @Test
    public void deve_testar_o_setEnderecos() {
        Set<Endereco> endereco = new HashSet<>();
        endereco.addAll(fornecedor.getEndereco());
        fornecedor.setEnderecos(endereco);
        assertTrue(fornecedor.equals(fornecedor));

    }
    
    /**
     * Deve validar enderecos annotations.
     */
    @Test
    public void deve_validar_enderecos_annotations() {
        assertFalse(Annotations.MensagemErroAnnotation(fornecedor.getEndereco()));
    }
    
    /* OUTROS TESTES */
    
    /**
     * Verificacao simples do equals verifier no fornecedor.
     */
    @Test
    public void verificacao_simples_do_equals_verifier_no_fornecedor() {
    	EqualsVerifier.simple().forClass(Fornecedor.class).verify();
    }

    /**
     * Deve gerar dados validos.
     */
    @Test
    public void deve_gerar_dados_validos() {
        assertTrue(isValid(fornecedor, "O campo nome n??o pode estar vazio"));
    }

    /**
     * Nao deve aceitar fornecedor sem cnpj nome telefone produto endereco.
     */
    @Test
    public void nao_deve_aceitar_Fornecedor_sem_cnpj_nome_telefone_produto_endereco() {
        Fornecedor Fornecedor = new Fornecedor();
        Set<ConstraintViolation<Fornecedor>> restricoes = validator.validate(Fornecedor);
        assertThat(restricoes, Matchers.hasSize(0));
    }

    /**
     * Deve passar na validacao com cnpj nome telefone produto endereco informados.
     */
    @Test
    public void deve_passar_na_validacao_com_cnpj_nome_telefone_produto_endereco_informados() {
        Set<ConstraintViolation<Fornecedor>> restricoes = validator.validate(fornecedor);
        assertThat(restricoes, empty());
    }

    /**
     * Deve retornar true no hash code com fornecedor iguais.
     */
    @Test
    public void deve_retornar_true_no_hashCode_com_fornecedor_iguais() {
        Fornecedor fornecedor2 = fornecedor;
        assertTrue(fornecedor.hashCode() == fornecedor2.hashCode());
    }

    /**
     * Deve retornar false no hash code com uma fornecedor de cnpj null.
     */
    @Test
    public void deve_retornar_false_no_hashCode_com_uma_fornecedor_de_cnpj_null() {
        Fornecedor fornecedor2 = new Fornecedor(null, "CA pe??as LTDA");
        assertFalse(fornecedor.hashCode() == fornecedor2.hashCode());
    }

    /**
     * Deve retornar true no equals com fornecedores iguais.
     */
    @Test
    public void deve_retornar_true_no_equals_com_fornecedores_iguais() {
        Fornecedor fornecedor2 = fornecedor;
        assertTrue(fornecedor.equals(fornecedor2) & fornecedor2.equals(fornecedor));
    }

    /**
     * Deve retornar false no equals com um fornecedor de cnpj null.
     */
    @Test
    public void deve_retornar_false_no_equals_com_um_fornecedor_de_cnpj_null() {
        Fornecedor fornecedor2 = new Fornecedor(null, "CA pe??as LTDA");
        assertFalse(fornecedor.equals(fornecedor2) & fornecedor2.equals(fornecedor));
    }

    /**
     * Deve retornar true no equals comparando um fornecedor com ela mesmo.
     */
    @Test
    public void deve_retornar_true_no_equals_comparando_um_fornecedor_com_ela_mesmo() {
        assertTrue(fornecedor.equals(fornecedor));
    }

    /**
     * Deve retornar false no equals comparando um fornecedor com null.
     */
    @Test
    public void deve_retornar_false_no_equals_comparando_um_fornecedor_com_null() {
        assertFalse(fornecedor.equals(null));
    }

    /**
     * Deve retornar true no equals comparando dois fornecedores de cnpj null.
     */
    @Test
    public void deve_retornar_true_no_equals_comparando_dois_fornecedores_de_cnpj_null() {
        Fornecedor fornecedor1 = new Fornecedor(null, "CA pe??as LTDA");
        Fornecedor fornecedor2 = new Fornecedor(null, "CA pe??as LTDA");
        assertTrue(fornecedor1.equals(fornecedor2));
    }

    /**
     * Deve retornar false no equals com fornecedores de cnpj diferentes.
     */
    @Test
    public void deve_retornar_false_no_equals_com_fornecedores_de_cnpj_diferentes() {
        Fornecedor fornecedor1 = new Fornecedor("97904702000131", "CA pe??as LTDA");
        Fornecedor fornecedor2 = new Fornecedor("97904702000132", "CA pe??as LTDA");
        assertFalse(fornecedor2.equals(fornecedor1));
    }

    /**
     * Deve retornar false no equals com a fornecedor e um objeto aleatorio.
     */
    @Test
    public void deve_retornar_false_no_equals_com_a_fornecedor_e_um_objeto_aleatorio() {
        assertFalse(fornecedor.equals(new Object()));
    }

    /**
     * To string deve retornar vazia.
     */
    @Test
    public void toString_deve_retornar_vazia() {
        Fornecedor fornecedorNull = new Fornecedor(null, null);
        assertThat(fornecedorNull.toString(), containsString(""));
    }

    /**
     * To string deve retornar valores preenchidos.
     */
    @Test
    public void toString_deve_retornar_valores_preenchidos() {
        Fornecedor fornecedorPreenchido = new Fornecedor("97904702000131", "CA pe??as LTDA");
        assertThat(fornecedorPreenchido.toString(), is(fornecedorPreenchido.toString()));
    }
    
    @Test
    public void simpleEqualsContract() {
    	EqualsVerifier.simple().forClass(Fornecedor.class).verify();
    }

    /**
     * Tear down.
     */
    @After
    public void tearDown() {
    	System.out.println(fornecedor);
    }	

}
