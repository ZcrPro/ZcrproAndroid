
#include <stdio.h>
#include "ax_util.h"

void printUint8Array(char *output, uint8_t *data, int dataLen){
	for(int i = 0; i < dataLen; i ++){
		sprintf(output + i * 2, "%02x", data[i]);
	}
	
	output[dataLen * 2] = '\0';
}