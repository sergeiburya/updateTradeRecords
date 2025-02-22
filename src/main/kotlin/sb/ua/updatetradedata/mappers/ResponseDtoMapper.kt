package sb.ua.updatetradedata.mappers

interface ResponseDtoMapper<M, D> {
    fun mapToDto(model: M): D
}