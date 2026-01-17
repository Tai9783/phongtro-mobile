package com.example.apptimphongtro.util


data class PriceRange(val min: Long?, val max: Long?)

fun parsePriceLabel(label: String):PriceRange?{
    val s= label.lowercase()
        .replace("triệu","")
        .replace(" ","")
        .replace("–", "-")
        .replace("—", "-")
    fun  toVnd(millionText: String): Long {
        return (millionText.replace(",",".").toDouble() * 1_000_000L).toLong()
    }

    return when{
        s.startsWith("<=")->{
            val max= toVnd(s.removePrefix("<="))
            PriceRange(null,max)
        }
        s.startsWith(">=")->{
            val min=toVnd(s.removePrefix(">="))
            PriceRange(min,null)
        }
        s.startsWith(">")->{
            val min=toVnd(s.removePrefix(">"))+1
            PriceRange(min,null)
        }
        s.contains("-")->{
            val part= s.split("-")
            if (part.size!=2) return null
            PriceRange(toVnd(part[0]),toVnd(part[1]))
        }
        else-> null

    }

}

