# Starten

## Docker

```
docker run --rm -dit -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" --name myelastic elasticsearch:7.9.3
```


# Benutzung

## Nodes anzeigen
```
http://localhost:9200/_cat/nodes?v&pretty
```

# Dokumente hochladen

## Alle Dokumente per shell

```
for file in /docs/*.json
do
  curl -X POST "localhost:9200/myindex/_doc/?pretty" -H 'Content-Type: application/json' --data-binary "@$file"
done
```
## Dokumente per PowerShell speichern
```
Invoke-WebRequest -Method POST -Uri 'http://localhost:9200/myindex/_doc/?pretty' -ContentType 'application/json' -InFile docs/doc_001.json
```

# Dokumente anzeigen

```
http://localhost:9200/myindex/_search
```

## Queries

```
{
  "query": {
    "match": {
      "name": "Peter"
    }
  }
}
```

## Query per PowerShell ausführen
```
Invoke-WebRequest -Method POST -Uri 'http://localhost:9200/myindex/_search' -ContentType 'application/json' -InFile queries/query.json
```
Ergebnis speichern
```
mkdir target
$r = Invoke-WebRequest -Method POST -Uri 'http://localhost:9200/myindex/_search' -ContentType 'application/json' -InFile queries/query.json
$r.RawContent > target\result.json
```