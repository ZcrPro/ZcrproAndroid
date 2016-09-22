
#include "stdio.h"
#include "ax_secure.h"
#include "ax_key_store.h"
#include "ax_cipher.h"
#include "ax_log.h"

uint8_t appSignature[16];


int hexChar2Int(char c){
	switch(c){
	case '0':
	case '1':
	case '2':
	case '3':
	case '4':
	case '5':
	case '6':
	case '7':
	case '8':
	case '9':
		return c - '0';
	case 'a':
	case 'b':
	case 'c':
	case 'd':
	case 'e':
	case 'f':
		return c - 'a' + 10;
	case 'A':
	case 'B':
	case 'C':
	case 'D':
	case 'E':
	case 'F':
		return c - 'A' + 10;;
	default:
		return -1;
	}
}

uint8_t hexChar2Uint8(char ch, char cl){
	uint8_t vh = (uint8_t) hexChar2Int(ch);
	uint8_t vl = (uint8_t) hexChar2Int(cl);
	
	uint8_t result = ((0x0f & vh) << 4) | (0x0f & vl);
	
	return result;
}

int decodeKey(uint8_t *output, char *key){
	logv("ax_cipher", "decodeKey begin, key: %s", key);
	
	int dataLen = strlen(key) / 2;
	uint8_t *data = malloc(dataLen);
	
	for(int i = 0; i < dataLen; i ++){
		data[i] = hexChar2Uint8(key[i * 2], key[i * 2 + 1]);
	}
	
	int outputLen = AxAesDecode(output, data, dataLen, appSignature);
	
	free(data);
	logv("ax_cipher", "output len: %d", outputLen);
	
	char decKey[outputLen + 1];
	
	memcpy(decKey, output, outputLen);
	decKey[outputLen] = '\0';
	
	logv("ax_cipher", "decodeKey: %s", decKey);
	
	return outputLen;
}

int getMsgSignKeyDeb(uint8_t *output){
	return decodeKey(output, KEY_MESSAGE_SIGN_DEB);
}

int getMsgSignKeyRel(uint8_t *output){
	return decodeKey(output, KEY_MESSAGE_SIGN_REL);
}

int getMsgFieldKeyDeb(uint8_t *output){
	return decodeKey(output, KEY_MESSAGE_FIELD_DEB);
}

int getMsgFieldKeyRel(uint8_t *output){
	return decodeKey(output, KEY_MESSAGE_FIELD_REL);
}

int getMsgKeyDeb(uint8_t *output){
	return decodeKey(output, KEY_MESSAGE_DEB);
}

int getMsgKeyRel(uint8_t *output){
	return decodeKey(output, KEY_MESSAGE_REL);
}

int getLocalKeyDeb(uint8_t *output){
	return decodeKey(output, KEY_LOCAL_DEB);
}

int getLocalKeyRel(uint8_t *output){
	return decodeKey(output, KEY_LOCAL_REL);
}
