import Palet
import Stack
import Route
import Truck

-- Crear las rutas
rutaCorta = newR ["Roma"]
rutaLarga = newR ["Roma", "Paris", "MDQ", "Berna"]
rutaVacia = newR []

-- Crear camión y palet
camion = newT 3 5 rutaLarga  -- 3 bahías, cada una con capacidad para 5 palets
palet = newP "Paris" 2  -- Palet con destino a "Paris" y peso de 2 toneladas

-- Pruebas
testRutaValida = inOrderR rutaLarga "Roma" "Paris" == True  -- "Roma" está antes que "Paris"
--testRutaVacia = inOrderR rutaVacia "Roma" "Paris" == False  -- No hay ciudades en la ruta

testCarga = netT (loadT camion palet) == (netT camion + netP palet)  -- El peso del camión debería aumentar por la carga del palet
camion2= loadT camion palet
testDescarga = netT (unloadT camion2  "Paris") == (netT camion2- 2)  -- El peso del camión debería disminuir después de descargar el palet con destino a "Paris"

-- Ejecutar las pruebas
main :: IO ()
main = do
    print testRutaValida
    --print testRutaVacia
    print testCarga
    print testDescarga
