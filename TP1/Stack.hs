module Stack ( Stack, newS, freeCellsS, stackS, netS, holdsS, popS)
  where

import Palet
import Route

data Stack = Sta [ Palet ] Int deriving (Eq, Show)

newS :: Int -> Stack                      -- construye una Pila con la capacidad indicada 
newS capacidad | capacidad <= 0 = error "La capacidad de la Pila debe ser un numero entero mayor a 0"
newS capacidad = Sta [] capacidad


freeCellsS :: Stack -> Int                -- responde la celdas disponibles en la pila
freeCellsS (Sta palets capacidad) = capacidad - (length palets)

weightAvailableS :: Palet -> Stack -> Bool -- indica si la pila puede aceptar un palet considerando que no puede superar 10 toneladas
weightAvailableS palet (Sta palets capacidad) = ((netP palet) + (netS (Sta palets capacidad)) <= 10)

stackS :: Stack -> Palet -> Stack         -- apila el palet indicado en la pila
stackS (Sta palets capacidad) palet | (weightAvailableS palet (Sta palets capacidad)) && ((freeCellsS (Sta palets capacidad))>=1) = (Sta (palet:palets) capacidad)
                                    | otherwise =  (Sta palets capacidad)

netS :: Stack -> Int                      -- responde el peso neto de los paletes en la pila
netS (Sta [] capacidad) = 0
netS (Sta palets capacidad) = sum ([netP p | p <- palets])


holdsS :: Stack -> Palet -> Route -> Bool -- indica si la pila puede aceptar el palet considerando las ciudades en la ruta
holdsS (Sta [] capacidad) palet ruta = inRouteR ruta (destinationP palet)
holdsS (Sta palets capacidad) palet ruta = inOrderR ruta (destinationP palet) (destinationP (head palets))


popS :: Stack -> String -> Stack          -- quita del tope los paletes con destino en la ciudad indicada
popS (Sta [] capacidad) ciudad = Sta [] capacidad 
-- popS (Sta (palet:palets) capacidad) ciudad | (destinationP palet) == ciudad = popS (Sta palets capacidad) ciudad | otherwise = Sta (palet:palets) capacidad
popS (Sta palets capacidad) ciudad | (destinationP (head palets)) == ciudad = popS (Sta (tail palets) capacidad) ciudad | otherwise = (Sta palets capacidad) 


rutaCamion = newR ["salta", "busan", "miami", "mdq", "chubut", "varsovia"]
p1 = newP "mdq" 20
p2 = newP "miami" 10
p3 = newP "salta" 15
p4 = newP "busan" 50

s1 = newS 60
s2 = stackS s1 p2
s3 = stackS s2 p1
s4 = stackS s3 p1
s5 = stackS s4 p1
--holdsS s3 p4 rutaCamion