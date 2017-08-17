package edu.gae.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;

import edu.domain.helper.Password;
import edu.gae.domain.constants.ReturnMessage;
import edu.gae.domain.entity.Login;
import edu.gae.domain.entity.User;
import edu.gae.domain.entity.base.SearcheableString;
import edu.gae.domain.entity.ref.UserProfile;
import edu.gae.domain.entity.ref.UserStatus;
import edu.gae.domain.exception.InfrastructureException;
import edu.gae.domain.repository.PersistableDao;
import edu.gae.domain.repository.datastore.DatastoreLoginDao;
import edu.gae.domain.repository.datastore.DatastoreUserDao;
import edu.gae.domain.repository.file.FileType;
import edu.gae.domain.repository.file.GCloudStorageFileDao;
import edu.gae.model.Page;
import edu.gae.model.UserDto;
import edu.gae.model.result.UserResultDto;
import edu.gae.service.UserService;
import edu.gae.service.converter.DozerHelper;

public class UserServiceImpl extends AbstractLifeCycleService<User, UserDto> implements UserService{
	
	private static final Logger log = Logger.getLogger(UserServiceImpl.class.getName());

	private static final String BUCKET = "appid.appspot.com";
	private static final String IMAGE_PREFIX = "user-image";
		
	private static UserService service;
	
	private UserServiceImpl() {}
	
	public static UserService getInstace(){
		if (service == null){
			service = new UserServiceImpl();
		}
		return service;
	}	

	@Override
	public List<UserResultDto> listByCriteria(User user, Map<String, Object> searchCriteria, Page page, List<String> order) {
		List<User> domain = getDao().list(user.getAncestor(), searchCriteria, page.getLimit(), page.getOffset(), order);
		Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
		return DozerHelper.map(mapper, domain, UserResultDto.class);
	}

	@Override
	protected PersistableDao<User> getDao() {
		return DatastoreUserDao.getInstance();
	}

	@Override
	protected Class<User> getDomainClass() {
		return User.class;
	}

	@Override
	protected Class<UserDto> getModelClass() {		
		return UserDto.class;
	}
	
	@Override
	protected void setCreateFields(User domain, User user) {
		super.setCreateFields(domain, user);		
		domain.setStatus(UserStatus.NEW);
	}
	
	@Override
	public UserDto changeImage(Long id, byte[] bytes, User user) {	
		User domain = getDao().get(id);
		validateOwnership(domain, user);
		String servingUrl = saveImage(id, bytes);
		changeOficinaLogo(domain, servingUrl);
		changeLoginLogo(user, domain);		
		return DozerBeanMapperSingletonWrapper.getInstance().map(domain, UserDto.class);		
	}
	
	@Override
	public UserDto createMaster(String email, String pass) {		
		User user = new User(new SearcheableString("Master"), email, null, UserProfile.MASTER, UserStatus.ACTIVE, true);
		user.setPassword(Password.getMD5(pass));
		user.setCreateDate(new Date());
		DatastoreUserDao.getInstance().addOrUpdate(user);
		return DozerBeanMapperSingletonWrapper.getInstance().map(user, getModelClass());
	}
	
	private String saveImage(Long id, byte[] bytes) {
		Image image = ImagesServiceFactory.makeImage(bytes);				

		BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
		ImagesService imagesService = ImagesServiceFactory.getImagesService();
		
		String fileName = String.format("%s-%s",IMAGE_PREFIX, id);
		String gcsFilePath = String.format("/gs/%s/%s", BUCKET, fileName);

		BlobKey blobKey = blobstoreService.createGsBlobKey(gcsFilePath);		
		imagesService.deleteServingUrl(blobKey);

		try {
			GCloudStorageFileDao.getInstance(BUCKET).save(bytes, fileName, FileType.equals(image.getFormat().toString()));
		} catch (IOException e) {
			throw new InfrastructureException(ReturnMessage.IO_ERROR, e);
		}
		
		ServingUrlOptions options = ServingUrlOptions.Builder.withGoogleStorageFileName(gcsFilePath);
		options.secureUrl(true);
		String servingUrl = imagesService.getServingUrl(options);
		return servingUrl;
	}
	
	private void changeOficinaLogo(User usuario, String servingUrl) {	
		log.info("URL Image: "+servingUrl);
		usuario.setLogo(servingUrl);	
		getDao().addOrUpdate(usuario);
	}

	private void changeLoginLogo(User user, User usuario) {
		Login login = DatastoreLoginDao.getInstance().findByEmail(user.getEmail());
		if (login != null && login.getUser() != null){
			login.getUser().setLogo(usuario.getLogo());
			DatastoreLoginDao.getInstance().addOrUpdate(login);
		}
	}
}
