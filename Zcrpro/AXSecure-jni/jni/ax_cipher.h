
#ifndef CIPHER_H
#define CIPHER_H

#include "aes.h"
#include "md5.h"

#define AES_BLOCK_LEN 16

extern int AxAesEncode(uint8_t* output, uint8_t* data, int len, uint8_t* key);
extern int AxAesDecode(uint8_t* output, uint8_t* data, int len, uint8_t* key);
extern int AxAesEncodeCBC(uint8_t* output, uint8_t* data, int len, uint8_t* key, uint8_t* iv);
extern int AxAesDecodeCBC(uint8_t* output, uint8_t* data, int len, uint8_t* key, uint8_t* iv);
extern int AxAesOutputDataSize(int dataLen);
extern int AxMd5(uint8_t* output, uint8_t* data, int len);

#endif /* CIPHER_H */