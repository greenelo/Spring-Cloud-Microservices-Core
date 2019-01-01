package com.sssseclipse.web.core.auth.token;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.SerializationUtils;

@Service
public class OauthTokenStoreSupporter {

	public String generateIdFromString(String value) {
		return DigestUtils.md5Hex(value);
	}
	
	public byte[] serialize(Object object) {
		return SerializationUtils.serialize(object);
	}

	public <T> T deserialize(byte[] object, Class<T> clazz) {
		return clazz.cast(SerializationUtils.deserialize(object));
	}
}
