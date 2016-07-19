import sys
from pyspark import SparkContext
from operator import add




sc = SparkContext(appName="part-c")
lines = sc.textFile("purchasedatatextfiles/person.txt")
persondata = lines.map(lambda x: x.split('\t'))
lines = sc.textFile("purchasedatatextfiles/purchase.txt")
purchasedata = lines.map(lambda x: x.split('\t'))
lines = sc.textFile("purchasedatatextfiles/product.txt")
proddata = lines.map(lambda x: x.split('\t'))

joindata = persondata.map(lambda x: (x[0],x[2])).join(purchasedata.map(lambda x: (x[0],x[3]))).map(lambda z: (z[1][1],z[1][0]))
catdata = joindata.join(proddata.map(lambda x: (x[0],x[2]))).map(lambda x: (x[1],1)).reduceByKey(add).map(lambda x: (x[0][1],x[0][0],x[1]))
print catdata.collect()
