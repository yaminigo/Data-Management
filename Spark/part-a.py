import sys
from pyspark import SparkContext
from operator import add
from sets import Set



sc = SparkContext(appName="part-a")
lines = sc.textFile("purchasedatatextfiles/product.txt")
proddata = lines.map(lambda x: x.split('\t'))
lines = sc.textFile("purchasedatatextfiles/purchase.txt")
purchasedata = lines.map(lambda x: x.split('\t'))

celldata =  proddata.filter(lambda z: z[2]=="cell phone").map(lambda x: (x[0],x[2]))

joindata = celldata.join(purchasedata.map(lambda x: (x[3],x[0])))

data = joindata.map(lambda x: x[1]).reduce(lambda a,b: list(a)+list(b))
print sc.parallelize(data).distinct().filter(lambda z: z != "cell phone").collect()