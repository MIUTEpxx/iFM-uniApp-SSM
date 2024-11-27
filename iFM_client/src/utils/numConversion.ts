export function numConversion(arg: number): string {
	if(arg.toString().length>=13){
			// return arg/1000000000000+"万亿"
			const volume= arg/1000000000000
			const realVal = parseFloat(volume).toFixed(2);
			return realVal+"万亿"
			
		}else if(arg.toString().length>=9){
			const volume= arg/100000000
			const realVal = parseFloat(volume).toFixed(2);
			return realVal+"亿"
		}else if(arg.toString().length> 4){
			const volume= arg/10000
			const realVal = parseFloat(volume).toFixed(2);
			return realVal+"万"
		}
		return arg
	}