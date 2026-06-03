import sys
import traceback
from lxml import etree

def sanitize_xmi(input_file, output_file):
    print(f"[*] Inizio sanitizzazione del file: {input_file}")
    
    # recover=True tenta di fare il parsing anche se Visual Paradigm 
    # genera XML leggermente malformato (succede più spesso di quanto credi)
    parser = etree.XMLParser(remove_blank_text=True, recover=True)
    
    try:
        tree = etree.parse(input_file, parser)
        root = tree.getroot()
        
        nodes_removed = 0
        
        # Approccio paranoico: ignoriamo i prefissi (che possono cambiare) 
        # e cerchiamo direttamente il nome locale del nodo.
        # VP mette il 99% della spazzatura qui.
        extensions = root.xpath("//*[local-name()='Extension']")
        
        # Cacciamo via qualsiasi tag legato a diagrammi visivi
        diagrams = root.xpath("//*[local-name()='Diagrams' or local-name()='diagrams' or local-name()='Diagram']")
        
        # Uniamo le liste e rimuoviamo i nodi in sicurezza
        for node in extensions + diagrams:
            parent = node.getparent()
            if parent is not None:
                parent.remove(node)
                nodes_removed += 1
                
        # Scrittura: utf-8 obbligatorio, pretty_print essenziale 
        # per ridurre il conteggio dei token (niente spazi inutili per l'LLM)
        tree.write(output_file, encoding='utf-8', xml_declaration=True, pretty_print=False)
        
        print(f"[+] Successo! Rimosse {nodes_removed} strutture di rumore visivo.")
        print(f"[+] File pulito salvato in: {output_file}")

    except etree.XMLSyntaxError as e:
        # Errore specifico se il file è corrotto in modo irreparabile
        print(f"[!] ERRORE CRITICO: Sintassi XML invalida nel file {input_file}.")
        print(f"Dettagli: {e}")
        sys.exit(1)
    except Exception as e:
        # Stack trace completo per fare debug vero se succede l'imprevedibile
        print(f"[!] ERRORE INASPETTATO:")
        print(traceback.format_exc())
        sys.exit(1)

if __name__ == "__main__":
    if len(sys.argv) != 3:
        print("Uso: python xmi_sanitizer.py <input.xmi> <output_pulito.xmi>")
        sys.exit(1)
        
    sanitize_xmi(sys.argv[1], sys.argv[2])
