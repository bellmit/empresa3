package br.com.contmatic.telefone;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

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
import org.junit.runners.MethodSorters;

import br.com.contmatic.easyRandom.EasyRandomClass;
import br.com.contmatic.util.Annotations;
import nl.jqno.equalsverifier.EqualsVerifier;

/**
 * The Class TelefoneTest.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TelefoneTest {

	/** The telefone. */
	private static Telefone telefone;

	/** The telefone DDD. */
	private static TelefoneDDD telefoneDDD;

	/** The tipo telefone. */
	private static TipoTelefone tipoTelefone;

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
		TelefoneTest.telefone = randomObject.telefoneRandomizerClass();
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		this.validator = factory.getValidator();
	}

	public boolean isValid(Telefone telefone, String mensagem) {
		validator = factory.getValidator();
		boolean valido = true;
		Set<ConstraintViolation<Telefone>> restricoes = validator.validate(telefone);
		for (ConstraintViolation<Telefone> constraintViolation : restricoes)
			if (constraintViolation.getMessage().equalsIgnoreCase(mensagem))
				valido = false;
		return valido;
	}

	/* TESTES NO DDD */

	/**
	 * Nao deve aceitar ddd nulo.
	 */
	@Test
	public void nao_deve_aceitar_ddd_nulo() {
		assertNotNull(telefone.getDdd());
	}

	/**
	 * Deve testar o get ddd esta funcionando corretamente.
	 */
	@Test
	public void deve_testar_o_getDdd_esta_funcionando_corretamente() {
		telefoneDDD = TelefoneDDD.valueOf("DDD11");
		assertTrue(telefoneDDD.getDdd() == 11);
	}

	/**
	 * Deve testar o get complemento esta funcionando corretamente.
	 */
	@Test
	public void deve_testar_o_getComplemento_esta_funcionando_corretamente() {
		telefoneDDD = TelefoneDDD.valueOf("DDD11");
		assertTrue(telefoneDDD.getComplemento().equals("S??o Paulo ??? SP"));
	}

	/**
	 * Deve validar ddd annotations.
	 */
	@Test
	public void deve_validar_ddd_annotations() {
		assertFalse(Annotations.MensagemErroAnnotation(telefone.getDdd()));
	}

	@Test
	public void deve_testar_o_setDdd() {
		telefone.setDdd(TelefoneDDD.DDD11);
		assertTrue(telefone.getDdd().equals(TelefoneDDD.DDD11));
	}

	/* TESTES NO NUMERO */

	/**
	 * Nao deve aceitar numero nulo.
	 */
	@Test
	public void nao_deve_aceitar_numero_nulo() {
		assertNotNull(telefone.getNumero());
	}

	/**
	 * Deve testar o get numero esta funcionando corretamente.
	 */
	@Test
	public void deve_testar_o_getNumero_esta_funcionando_corretamente() {
		telefone.setNumero("927219389");
		assertThat(telefone.getNumero(), containsString("927219389"));
	}

	@Test
	public void nao_deve_aceitar_espacos_em_branco_no_numero() {
		assertFalse(telefone.getNumero().trim().isEmpty());
	}

	@Test
	public void deve_aceitar_numero_valido() {
		telefone.setNumero("946756054");
		assertTrue(isValid(telefone, "O campo Numero est?? invalido"));
	}

	@Test
	public void nao_deve_aceitar_numero_com_espa??o() {
		telefone.setNumero("94675 6054");
		assertFalse(isValid(telefone, "O campo Numero est?? invalido"));
	}

	@Test
	public void nao_deve_aceitar_letras_no_numero() {
		telefone.setNumero("94675G054");
		assertFalse(isValid(telefone, "O campo Numero est?? invalido"));
	}

	@Test
	public void nao_deve_aceitar_caracter_especial_no_numero() {
		telefone.setNumero("94675@#$4");
		assertFalse(isValid(telefone, "O campo Numero est?? invalido"));
	}

	/**
	 * Deve validar numero annotations.
	 */
	@Test
	public void deve_validar_numero_annotations() {
		assertFalse(Annotations.MensagemErroAnnotation(telefone.getNumero()));
	}

	/* TESTES NO TIPO TELEFONE */

	/**
	 * Deve testar o get descricao esta funcionando corretameante.
	 */
	@Test
	public void deve_testar_o_getDescricao_esta_funcionando_corretameante() {
		tipoTelefone = TipoTelefone.CELULAR;
		assertTrue(tipoTelefone.getDescricao().equals("Celular"));
	}

	/**
	 * Deve testar o get tamanho esta funcionando corretameante.
	 */
	@Test
	public void deve_testar_o_getTamanho_esta_funcionando_corretameante() {
		tipoTelefone = TipoTelefone.CELULAR;
		assertTrue(tipoTelefone.getTamanho() == 9);
	}

	/**
	 * Deve validar tipo telefone annotations.
	 */
	@Test
	public void deve_validar_tipo_telefone_annotations() {
		assertFalse(Annotations.MensagemErroAnnotation(telefone.getTipoTelefone()));
	}

	/**
	 * Nao deve aceitar telefone nulo.
	 */
	@Test
	public void nao_deve_aceitar_telefone_nulo() {
		assertNotNull(telefone.getTipoTelefone());
	}

	/* OUTROS TESTES */

	/**
	 * Verificacao simples do equals verifier no telefone;
	 */
	@Test
	public void verificacao_simples_do_equals_verifier_no_fornecedor() {
		EqualsVerifier.simple().forClass(Telefone.class).verify();
	}

	/**
	 * Deve gerar dados validos.
	 */
	@Test
	public void deve_gerar_dados_validos() {
		Set<ConstraintViolation<Telefone>> constraintViolations = validator.validate(telefone);
		assertEquals(0, constraintViolations.size());
	}

	/**
	 * Nao deve aceitar cliente sem cpf nome telefone boleto.
	 */
	@Test
	public void nao_deve_aceitar_cliente_sem_cpf_nome_telefone_boleto() {
		Telefone telefone = new Telefone();
		Set<ConstraintViolation<Telefone>> restricoes = validator.validate(telefone);
		assertThat(restricoes, Matchers.hasSize(0));
	}

	/**
	 * Deve passar na validacao com cpf nome telefone boleto informados.
	 */
	@Test
	public void deve_passar_na_validacao_com_cpf_nome_telefone_boleto_informados() {
		Set<ConstraintViolation<Telefone>> restricoes = validator.validate(telefone);
		assertThat(restricoes, empty());
	}

	/**
	 * Deve retornar false no hash code com um endereco de numero null.
	 */
	@Test
	public void deve_retornar_false_no_hashCode_com_um_endereco_de_numero_null() {
		Telefone telefone2 = new Telefone(null, null, null);
		assertFalse(telefone.hashCode() == telefone2.hashCode());
	}

	/**
	 * Deve retornar true no equals comparando dois enderecos de cep null.
	 */
	@Test
	public void deve_retornar_true_no_equals_comparando_dois_enderecos_de_cep_null() {
		Telefone telefone1 = new Telefone(null, null, null);
		Telefone telefone2 = new Telefone(null, null, null);
		assertTrue(telefone1.equals(telefone2));
	}
	
    @Test
    public void simpleEqualsContract() {
    	EqualsVerifier.simple().forClass(Telefone.class).verify();
    }

	/**
	 * Tear down.
	 */
	@After
	public void tearDown() {
		System.out.println(telefone);
	}

}
