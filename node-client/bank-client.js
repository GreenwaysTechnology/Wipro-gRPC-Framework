const grpc = require('grpc')
const protoloader = require('@grpc/proto-loader')

const packageDef = protoloader.loadSync('proto/BankService.proto')
const protoDesc = grpc.loadPackageDefinition(packageDef)

const client = new protoDesc.BankService('localhost:8080', grpc.credentials.createInsecure())

client.getBalance({accountNumber: 2}, (err, balance) => {
    if(err){
        console.error('something bad happened')
    }else{
        console.log('Received : ' + balance.amount)
    }
})