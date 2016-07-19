import sys
from pyspark import SparkContext

sc = SparkContext(appName="part-d")
lines = sc.textFile("purchasedatatextfiles/product.txt")
proddata = lines.map(lambda x: x.split('\t'))
pricedetails= proddata.map(lambda x: (x[2],(x[1],1))).reduceByKey(lambda a,b: (float(a[0])+float(b[0]),a[1]+b[1]))
print pricedetails.map(lambda (x, (y, z)): (x, float(y)/z)).collect()