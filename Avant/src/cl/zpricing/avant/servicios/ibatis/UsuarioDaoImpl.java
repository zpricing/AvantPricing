
package cl.zpricing.avant.servicios.ibatis;

import java.util.ArrayList;
import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

import cl.zpricing.avant.model.Usuario;

/**
 * <b>Descripci�n de la Clase</b>
 * Implementacion de UsuarioDao
 * 
 * Registro de versiones:
 * <ul>
 *   <li>1.0 31-12-2008 Oliver Cordero: versi�n inicial.</li>
 * </ul>
 * <P>
 *  <B>Todos los derechos reservados por ZhetaPricing.</B>
 * <P>
 */ 
public class UsuarioDaoImpl extends SqlMapClientDaoSupport implements cl.zpricing.avant.servicios.UsuarioDao {

	private ShaPasswordEncoder encoder;
	
	public UsuarioDaoImpl() {
		encoder = new ShaPasswordEncoder();
	}
	
	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.UsuarioDao#actualizarUsuario(cl.zpricing.revman.model.Usuario)
	 */
	@Override
	public void actualizarUsuario(Usuario usuario) {
		getSqlMapClientTemplate().update("updateUsuario",usuario);
	}
	
	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.UsuarioDao#agregarUsuario(cl.zpricing.revman.model.Usuario)
	 */
	@Override
	public boolean agregarUsuario(Usuario usuario) {
		String pass = encoder.encodePassword(usuario.getPassword(),usuario.getUsuario());
		usuario.setPassword(pass);
		getSqlMapClientTemplate().insert("insertarUsuario", usuario);		
		return true;	
	}
	
	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.UsuarioDao#eliminarUsuario(cl.zpricing.revman.model.Usuario)
	 */
	@Override
	public boolean eliminarUsuario(Usuario usuario) {		
		try{
		getSqlMapClientTemplate().insert("deleteUsuario", usuario.getId());
		}catch(Exception e)
		{
		return false;
		}
		return true;
		
	}

	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.UsuarioDao#obtenerUsuario(int)
	 */
	@Override
	public Usuario obtenerUsuario(int id) {	
		return (Usuario) getSqlMapClientTemplate().queryForObject("getUsuario",id);	
	}
	
	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.UsuarioDao#obtenerUsuarioByName(java.lang.String)
	 */
	public Usuario obtenerUsuarioByName(String user) {	
		return (Usuario) getSqlMapClientTemplate().queryForObject("getUsuarioByName",user);
	}
	
	/* (non-Javadoc)
	 * @see cl.zpricing.revman.servicios.UsuarioDao#obtenerTodos()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Usuario> obtenerTodos() {
		List<Usuario> users = new ArrayList<Usuario>();
		users = (List<Usuario>) getSqlMapClientTemplate().queryForList("obtenerTodos");
		return users;
	}
 }
