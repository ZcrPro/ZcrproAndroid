
#include "ax_log.h"
#include "ax_cipher.h"

int AxAesEncodeCBC(uint8_t* output, uint8_t* data, int len, uint8_t* key, uint8_t* iv){
	logv("ax_cipher", "AxAesEncode3 begin");
	int outputDataLen = AxAesOutputDataSize(len);
	
	// TODO use malloc to create buffer
	logv("ax_cipher", "AxAesEncode3 create buffer");
	uint8_t *buf = malloc(outputDataLen);
	
	int paddingNum = outputDataLen - len;
	logv("ax_cipher", "AxAesEncode3 padding %d", paddingNum);
	memset(buf, paddingNum, outputDataLen);
	memcpy(buf, data, len);
		
	logv("ax_cipher", "AxAesEncode3 do cipher");
	AES128_CBC_encrypt_buffer(output, buf, outputDataLen, key, iv);
	
	free(buf);
	logv("ax_cipher", "AxAesEncode3 end");
	return outputDataLen;
}

int AxAesDecodeCBC(uint8_t* output, uint8_t* data, int len, uint8_t* key, uint8_t* iv){
	logv("ax_cipher", "AxAesEncode3 begin");
	
	if(len % AES_BLOCK_LEN  != 0){
		logv("ax_cipher", "AxAesDecode end error: illegal data size: %d", len);
		return -1;
	}
	
	int blockCount = len / AES_BLOCK_LEN;
	logv("ax_cipher", "AxAesEncode3 do cipher");
	for(int i = 0; i < blockCount; i ++){
		AES128_CBC_decrypt_buffer(output + i * AES_BLOCK_LEN, data + i * AES_BLOCK_LEN, 16, i != 0? 0: key, i != 0? 0: iv);
	}
	
	int paddingLen = output[len - 1];
	logv("ax_cipher", "AxAesEncode3 unpadding padding len: %d", paddingLen);
	
	logv("ax_cipher", "AxAesEncode3 end");
	return len - paddingLen;
}

int AxAesEncode(uint8_t* output, uint8_t* data, int len, uint8_t* key){
	logv("ax_cipher", "AxAesEncode begin");
	
	int bufLen = AES_BLOCK_LEN;
	int outputDataLen = AxAesOutputDataSize(len);
	uint8_t buf[bufLen];
	
	int blockCount = len / bufLen + 1;
	logv("ax_cipher", "AxAesEncode block count: %d", blockCount);
	for(int i = 0; i < len / bufLen + 1; i ++){
		int offset = i * bufLen;

		// padding
		int isTail = (offset + bufLen) > len;
		logv("ax_cipher", "AxAesEncode block %d is tail: %d", i, isTail);
		if(isTail){
			int paddingNum = outputDataLen - len;
			logv("ax_cipher", "AxAesEncode padding: %d", paddingNum);
			memset(buf, paddingNum, bufLen);
		}
		
		logv("ax_cipher", "AxAesEncode copy block data %d-%d", i * bufLen, isTail? len - offset: bufLen);
		memcpy(buf, data + offset, isTail? len - offset: bufLen);
		
		AES128_ECB_encrypt(buf, key, output + offset);
	}
		
	logv("ax_cipher", "AxAesEncode end");
	return outputDataLen;
}

int AxAesOutputDataSize(int dataLen){
	return (dataLen / AES_BLOCK_LEN + 1) * AES_BLOCK_LEN;
}

int AxAesDecode(uint8_t* output, uint8_t* data, int len, uint8_t* key){
	logv("ax_cipher", "AxAesDecode begin");
	if(len % AES_BLOCK_LEN  != 0){
		logv("ax_cipher", "AxAesDecode end error: illegal data size: %d", len);
		return -1;
	}
	
	for(int i = 0; i < len / AES_BLOCK_LEN; i ++){
		int offset = i * AES_BLOCK_LEN;
		AES128_ECB_decrypt(data + offset, key, output + offset);
	}
	
	// unpadding
	int paddingLen = output[len - 1];
	logv("ax_cipher", "AxAesDecode padding: %d", paddingLen);
	int outputLen = len - paddingLen;
	
	logv("ax_cipher", "AxAesDecode end");
	return outputLen;
}

int AxMd5(uint8_t* output, uint8_t* data, int len){
	logv("ax_cipher", "AxMd5 begin");
	struct md5_ctx ctx;
	md5_init(&ctx);
	
	int offset = 0;
	while(offset < len){
		int blockSize = ((offset + MD5_BUFFER) > len)? len - offset: MD5_BUFFER;
		ctx.size = blockSize;
		memcpy(ctx.buf, data + offset, ctx.size);
		md5_update(&ctx);
		
		offset += blockSize;
	}

	md5_final(output, &ctx);
	md5_uninit(&ctx);

	logv("ax_cipher", "AxMd5 end");
	return 16;
}

