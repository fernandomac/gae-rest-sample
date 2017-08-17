package edu.gae.domain.repository.file;

public enum FileType {	
	
	JPEG("data:image/jpeg;base64", "JPEG", "jpeg", "image/jpeg"),
	PNG("data:image/png;base64", "PNG", "png", "image/png");
	
	private final String encode;
	private final String key;
	private final String extension;
	private final String mimeType;

	private FileType(final String encode, final String key, final String extension, final String mimeType){
		this.encode = encode;
		this.key = key;
		this.extension = extension;
		this.mimeType = mimeType;
	}

	public String getEncode() {
		return encode;
	}

	public String getKey() {
		return key;
	}

	public String getExtension() {
		return extension;
	}

	public String getMimeType() {
		return mimeType;
	}

	public static FileType equals(String input){
		for (FileType imageType : FileType.values()){
			if (imageType.getEncode().equalsIgnoreCase(input) 
					|| imageType.getMimeType().equalsIgnoreCase(input)
					|| imageType.getExtension().equalsIgnoreCase(input)
					|| imageType.getKey().equalsIgnoreCase(input)){
				return imageType;
			}
		}
		System.err.println("ImageType not found: "+input);
		return null;
		
	}
	
	
	
}
