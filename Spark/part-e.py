import sys
from pyspark import SparkContext
from sets import Set

def sumf(iterator):
	for v in iterator:
		yield v
		
sc = SparkContext(appName="part-e")
lines = sc.textFile("purchasedatatextfiles/purchase.txt")
purchasedata = lines.map(lambda x: x.split('\t')[2])
rdd = sc.parallelize(purchasedata.collect(),2)
print "No. of different stores in purchase table: " + str(len(Set(rdd.mapPartitions(sumf).collect())))