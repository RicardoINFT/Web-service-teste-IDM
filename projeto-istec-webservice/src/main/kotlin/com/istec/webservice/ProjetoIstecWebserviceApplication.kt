package com.istec.webservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URL
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import org.springframework.http.MediaType
import org.springframework.stereotype.Repository
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseBody
import org.w3c.dom.Text
import javax.annotation.processing.Generated

@SpringBootApplication
class ProjetoIstecWebserviceApplication

fun main(args: Array<String>) {
	runApplication<ProjetoIstecWebserviceApplication>(*args)
}
@RestController
@RequestMapping("/Musica")
class GET
{

	@GetMapping("/{q}/{w}", produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
	public fun Letra(@PathVariable q : String, @PathVariable w :String) : String{
		val musica  = "https://api.vagalume.com.br/search.php?art=${q}&mus=${w}"
		val coisaurl = URL(musica)
		val json = Json.parseToJsonElement(coisaurl.readText())
		val json1 = json.jsonObject.toMap()
		val x = json1["mus"].toString()
		var string = x.toString()
		string = string.replace("\\[".toRegex(), "").replace("\\]".toRegex(), "")
		val json2 = Json.parseToJsonElement(string)
		val s = json2.jsonObject.toMap()
		val k:String = s["text"].toString()
		val l : String = "\""
		val result : String = k.replace(l,"\n")
		val result2 : String = result.replace("\n","\n")
		val asd : String = q.replace("-"," ")
		val asd2 : String = w.replace("-"," ")
		letra = result2
		autor = asd
		nomemusica = asd2
		return "Artista- "+asd+"\nMusica- "+asd2+"\nLetra- "+ result2
	}
	var lista = mutableListOf<Artista>()
	var letra : String = "";
	var autor : String = "";
	var nomemusica : String = "";
	@GetMapping("/Artistas")
	fun receber(): List<Artista>{
		return lista
	}

	@GetMapping("/{q}")
	fun receberporautor(@PathVariable q : String): Artista?{
		return lista.find{ x ->
			x.nomemusica == q }
	}

	@GetMapping("/Historico")
	fun tudo(): List<Artista>{
		return lista
	}

	@PostMapping("/Guardar", consumes = ["application/json"])
	fun postar(@RequestBody artista : Artista): String{
		artista.nomeautor = autor
		artista.nomemusica = nomemusica
		artista.letra = letra
		lista.add(artista)
		return "Musica adicionada - ${nomemusica}"
	}

	@DeleteMapping("/Delete/{s}")
	fun delete(@PathVariable s : String): String{
		var asd = lista.find{ x ->
			x.nomemusica == s}
		lista.remove(asd)
		return "Musica removida - ${s}"
	}

	@PutMapping("/Update/{b}/{l}",consumes = ["application/json"])
	fun put(@RequestBody artista:Artista,@PathVariable b:String,@PathVariable l :String): String{
		var asd = lista.find{ x ->
			x.nomemusica == b}
		val id1 = lista.indexOf(asd)
		artista.nomemusica = l
		artista.letra = asd!!.letra
		artista.nomeautor = asd!!.nomeautor
		lista.set(id1,artista)
		return "Musica atualizada - ${b} para ${l}"
	}

	data class Artista(var nomeautor:String, var nomemusica:String, var letra : String)
}


