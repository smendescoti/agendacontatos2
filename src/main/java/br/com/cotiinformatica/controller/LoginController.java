package br.com.cotiinformatica.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.cotiinformatica.entities.Usuario;
import br.com.cotiinformatica.repositories.UsuarioRepository;

@Controller
public class LoginController {

	// m�todo para mapear a rota de navega��o da p�gina de login,
	// definindo-a como p�gina inicial do sistema.
	@RequestMapping(value = "/") // mapeando a rota raiz do projeto
	public ModelAndView login() {

		// definindo o nome da p�gina que ser� exibida pelo sistema
		ModelAndView modelAndView = new ModelAndView("login");
		return modelAndView;
	}

	// m�todo para capturar a requisi��o de autentica��o de usu�rio
	@RequestMapping(value = "/autenticar-usuario", method = RequestMethod.POST)
	public ModelAndView autenticarUsuario(HttpServletRequest request) {

		ModelAndView modelAndView = new ModelAndView("login");

		try {

			String email = request.getParameter("email");
			String senha = request.getParameter("senha");

			UsuarioRepository usuarioRepository = new UsuarioRepository();
			Usuario usuario = usuarioRepository.findByEmailAndSenha(email, senha);

			if (usuario == null) {

				modelAndView.addObject("mensagem_erro", "Acesso negado.");
			} else {

				// salvar os dados do usu�rio em sess�o
				request.getSession().setAttribute("usuario_auth", usuario);

				// redirecionar o usu�rio para a primeira p�gina da �rea Admin do projeto
				modelAndView.setViewName("redirect:admin/pagina-inicial");
			}
		} catch (Exception e) {

			modelAndView.addObject("mensagem_erro", "Falha ao autenticar.");
			e.printStackTrace();
		}

		return modelAndView;
	}
	
	//m�todo para mapear a rota de logout do usu�rio
	@RequestMapping(value = "/logout")
	public ModelAndView logout(HttpServletRequest request) {
	
		//destruir a sess�o do usu�rio
		request.getSession().removeAttribute("usuario_auth");
		//apagar todo o conteudo gravado em sess�o
		request.getSession().invalidate();
		
		//redirecionar o usu�rio de volta para a rota do sistema (login)
		ModelAndView modelAndView = new ModelAndView("redirect:/");
		return modelAndView;
	}
}














